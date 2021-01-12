package org.ray.wx.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.ray.wx.exceptions.WxServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * request信息
 *
 * @author
 */
@Component
public class WxShareUtil {

    protected Logger logger = LoggerFactory.getLogger(WxShareUtil.class);

    @Value("${wx.appid}")
    private String appId;//注意 这里必须填写   在你得公众号里面找
    @Value("${wx.appsecret}")
    private String secret;//注意 这里必须填写  在你得公众号里面找

    @Value("${wx.redisKey.accessToken}")
    private String accessTokenRedisKey;//注意 这里必须填写  在你得公众号里面找

    @Value("${wx.redisKey.jsApiTicket}")
    private String jsApiTicketRedisKey;//注意 这里必须填写  在你得公众号里面找


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final String apiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";


    /**
     * 生成微信权限验证的参数
     *
     * @return
     */
    public String getAccessToken() {
        Object accessToken = redisTemplate.opsForValue().get(accessTokenRedisKey);
        if (accessToken != null) {
            return accessToken.toString();
        } else {
            String url = accessTokenUrl.replace("APPID", appId).replace("SECRET", secret);
            logger.info("微信分享-获取accessToken的url:{}", url);
            JSONObject access_token_json = getUrlInfo(url, "GET", null);
            if (access_token_json == null) {
                return null;
            }
            logger.info("微信分享-获取accessToken的结果:{}", access_token_json != null ? access_token_json.toJSONString() : null);
            String access_token = access_token_json.getString("access_token");
            Long expires_in = access_token_json.getLongValue("expires_in");
            redisTemplate.opsForValue().set(accessTokenRedisKey, expires_in, expires_in - 60 * 1, TimeUnit.SECONDS);
            if (StringUtils.isBlank(access_token)) {
                logger.info("微信分享-获取accessToken失败");
            }
            return access_token;
        }
    }


    /**
     * 生成微信权限验证的参数
     *
     * @param url
     * @return
     */
    public Map<String, String> getWxShareParam(String url) {
        String jsApiTicket = getJsApiTicket();
        Map<String, String> ret = new HashMap<String, String>();
        String nonceStr = createNonceStr();
        String timestamp = createTimestamp();
        String string1;
        String signature = "";
        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsApiTicket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        logger.info("String1=====>" + string1);
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
            logger.info("signature=====>" + signature);
        } catch (NoSuchAlgorithmException e) {
            logger.error("WeChatController.makeWXTicket=====Start");
            logger.error(e.getMessage(), e);
            logger.error("WeChatController.makeWXTicket=====End");
        } catch (UnsupportedEncodingException e) {
            logger.error("WeChatController.makeWXTicket=====Start");
            logger.error(e.getMessage(), e);
            logger.error("WeChatController.makeWXTicket=====End");
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsApiTicket);
        ret.put("nonceStr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appid", appId);

        return ret;
    }


    //字节数组转换为十六进制字符串
    private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    //生成随机字符串
    private String createNonceStr() {
        return UUID.randomUUID().toString();
    }

    //生成时间戳
    private String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }


    /**
     * 获取ticket
     *
     * @return
     */
    public String getJsApiTicket() {
        Object ticket = redisTemplate.opsForValue().get(jsApiTicketRedisKey);
        if (ticket != null) {
            return ticket.toString();
        } else {
            String accessToken = getAccessToken();
            if (StringUtils.isBlank(accessToken)) {
                throw new WxServiceException("获取accessToken失败");
            }
            String requestUrl = apiTicketUrl.replace("ACCESS_TOKEN", accessToken);
            logger.info("getJsApiTicket.requestUrl====>" + requestUrl);
            String restResult = new RestTemplate().getForObject(requestUrl, String.class);
            JSONObject result = JSON.parseObject(restResult);
            logger.info("获取ticket结果：" + result);
            if (result != null) {
                logger.info("ticketInfo====>" + result.toJSONString());
                String jsApiTicket = result.getString("ticket");
                Long ticketExpireTime = result.getLongValue("expires_in");
                redisTemplate.opsForValue().set(jsApiTicketRedisKey, jsApiTicket, ticketExpireTime - 60, TimeUnit.SECONDS);
                return jsApiTicket;
            }
        }

        return null;
    }

    //
    private JSONObject getUrlInfo(String requestURL, String method, String json) {
        try {
            URL get_url = new URL(requestURL);
            // 将url 以 open方法返回的urlConnection 连接强转为HttpURLConnection连接
            // (标识一个url所引用的远程对象连接)
            // 此时cnnection只是为一个连接对象,待连接中
            HttpURLConnection httpURLConnection = (HttpURLConnection) get_url
                    .openConnection();
            // 设置请求方式为post
            httpURLConnection.setRequestMethod(method);
            // 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
            httpURLConnection.setDoOutput(true);
            // 设置连接输入流为true
            httpURLConnection.setDoInput(true);
            // post请求缓存设为false
            httpURLConnection.setUseCaches(false);
            // 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
            // application/x-javascript text/xml->xml数据
            // application/x-javascript->json对象
            // application/x-www-form-urlencoded->表单数据
            // ;charset=utf-8 必须要，不然妙兜那边会出现乱码
            httpURLConnection.setRequestProperty("Content-type",
                    "application/json;charset=utf-8");
            // 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
            httpURLConnection.connect();

            // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
            OutputStreamWriter out = new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8");
            if (method.equals("POST")) {
                out.append(json);
            }
            out.flush();
            out.close();

            // 连接发起请求,处理服务器响应 (从连接获取到输入流并包装为bufferedReader)
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpURLConnection.getInputStream(), "UTF-8"));
            // 读取数据操作
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            //转换成json
            JSONObject jsonObj = JSONObject.parseObject(buffer.toString());
            reader.close();
            return jsonObj;
        } catch (Exception e) {
            logger.error("系统发生异常", e);
            return null;
        }

    }
}