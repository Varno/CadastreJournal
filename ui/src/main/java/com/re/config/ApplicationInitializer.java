package com.re.config;

import com.re.ContextConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.vaadin.spring.boot.VaadinAutoConfiguration;
import org.vaadin.spring.config.VaadinConfiguration;
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.re")
public class ApplicationInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        return application.showBanner(false)
                .sources(ContextConfiguration.class)
                .sources(VaadinAutoConfiguration.class, VaadinConfiguration.class)
                .sources(WebConfig.class)
                .sources(SecurityConfig.class);
    }
}
