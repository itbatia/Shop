package com.itbatia.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = "com.itbatia.app.rest")
public class ApplicationSwaggerConfig {

    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo("REST Shop backend Api Documentation", // Title
                "This is REST API documentation of the Spring Shop backend", // Description
                "1.0", // Version
                "TeamGetter backend terms of service", // Terms of Service
                new Contact(
                        "Sergey Botyan",
                        " ",
                        "botyan.sergey@gmail.com"), // Contact
                "Apache 2.0", // License
                "http://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
    }
}
