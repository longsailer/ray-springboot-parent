package org.ray.operation.info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: wenzhang.fu
 * @Date: 2019-07-01 17:21
 * @Description: 拦截器
 */
@Configuration
public class LogConfigure extends WebMvcConfigurerAdapter {

    /*
     * 将logInterceptor放入到spring容器中管理
     */
    @Bean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

    /*
     * 实现此方法添加拦截器
     * addPathPatterns  拦截路径
     * excludePathPatterns() (不拦截的路径数组)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截路径，表示此路径下的所有地址都会先执行此拦截器，通过之后才能访问Controller
        String[] addPathPatterns = {"/*"};
        registry.addInterceptor(logInterceptor()).addPathPatterns(addPathPatterns);
    }

}
