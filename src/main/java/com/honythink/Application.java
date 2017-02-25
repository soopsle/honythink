
package com.honythink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
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
public class Application {
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

