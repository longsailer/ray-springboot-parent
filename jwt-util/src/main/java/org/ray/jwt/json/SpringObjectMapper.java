/**
 * SpringObjectMapper.java
 * @author: 杨洲
 * @date: 2018年5月18日
 */
package org.ray.jwt.json;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.ray.encrypt.utils.SecretAlgorithm;
import org.ray.jwt.token.JsonWebToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SpringObjectMapper implements JsonMapper<JsonWebToken> {
	private static Logger log = LoggerFactory.getLogger(SpringObjectMapper.class);
	private ObjectMapper om = new ObjectMapper();

	public String objectToJson(JsonWebToken t) {
		try {
			String payLoad = om.writeValueAsString(t.getPayLoad());
			String sign = SecretAlgorithm.compute(payLoad, SecretAlgorithm.MD5);
			t.setSign(sign);
			return om.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			log.error("A object write to string of json.", e);
			return null;
		}
	}

	public JsonWebToken jsonToObject(String json) {
		try {
			return om.readValue(json, new TypeReference<JsonWebToken>(){});
		} catch (IOException e) {
			log.error("A string of json write to object.", e);
			return null;
		}
	}
}
