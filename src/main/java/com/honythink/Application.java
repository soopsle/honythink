
package com.honythink;

import javax.servlet.MultipartConfigElement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author
 * @version : 1.00
 * @Copyright http://www.onehome.cn/
 * @Create Time : 2016年10月12日 上午11:33:59
 * @Description : SpringApplication项目入口
 * @History：Editor version Time Operation Description*
 *
 */
@SpringBootApplication
@ComponentScan
@MapperScan("com.honythink.db.mapper")
public class Application {
    @Bean 
    public MultipartConfigElement multipartConfigElement() { 
            MultipartConfigFactory factory = new MultipartConfigFactory();
            //// 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
            factory.setMaxFileSize("20MB"); //KB,MB
            /// 设置总上传数据总大小
            factory.setMaxRequestSize("100MB"); 
            //Sets the directory location where files will be stored.
//            factory.setLocation("/usr/local/honythink/");
            return factory.createMultipartConfig(); 
    } 
    
    @Bean
    EmbeddedServletContainerCustomizer containerCustomizer() throws Exception {
        return (ConfigurableEmbeddedServletContainer container) -> {
            if (container instanceof TomcatEmbeddedServletContainerFactory) {
                TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
                tomcat.addConnectorCustomizers(
                    (connector) -> {
                        connector.setMaxPostSize(10000000); // 10 MB
                    }
                );
            }
        };
    }
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}

