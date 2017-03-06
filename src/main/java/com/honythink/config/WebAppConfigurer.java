package com.honythink.config;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter implements EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(WebAppConfigurer.class);

    private RelaxedPropertyResolver propertyResolver;

    @Value("${spring.datasource.url}")
    private String myUrl;
    
    /**
     * 
     * @return
     * @about version ：1.00
     * @auther : 
     * @Description ：处理 "/" 根路径请求
     */
    @Bean
    public WebMvcConfigurerAdapter forwardToIndex() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("index");
            }
        };
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
//        registry.addInterceptor(systemInterceptor).addPathPatterns("/**").excludePathPatterns("/").excludePathPatterns("/test");
//        super.addInterceptors(registry);
    }

    /**
     * 这个方法只是测试实现EnvironmentAware接口，读取环境变量的方法。
     */
    @Override
    public void setEnvironment(Environment env) {
        log.info(env.getProperty("JAVA_HOME"));
        log.info(myUrl);
        String str = env.getProperty("spring.datasource.url");
        log.info(str);
        propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
        String url = propertyResolver.getProperty("url");
        log.info(url);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> httpMessageConverter : converters) {
            // 为 MappingJackson2HttpMessageConverter 添加 "application/javascript" 支持，用于响应JSONP的Content-Type
            if (httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter convert = (MappingJackson2HttpMessageConverter) httpMessageConverter;
                List<MediaType> medisTypeList = new ArrayList<>(convert.getSupportedMediaTypes());
                medisTypeList.add(MediaType.valueOf("application/javascript;charset=UTF-8"));
                convert.setSupportedMediaTypes(medisTypeList);
                break;
            }
        }
        super.extendMessageConverters(converters);
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        return processor;
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        super.extendHandlerExceptionResolvers(exceptionResolvers);
    }

}
