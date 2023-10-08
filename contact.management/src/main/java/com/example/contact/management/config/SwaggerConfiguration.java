package com.example.contact.management.config;

import org.springframework.context.annotation.Bean;
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
public class SwaggerConfiguration {

    private static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
    private static final String API_INFO_TITLE = "Contact management system Api Documentation by Yash Saxena";
    private static final String API_INFO_DESCRIPTION = "First doSignUp and register user , after that doLogin with same username and password there you will get Jwt Token , which is used in all other apis in header";
    private static final String API_INFO_VERSION = "1.0";
    private static final String API_INFO_TERMS_AND_SERVICE_URI = null;
    private static final Contact API_INFO_CONTACT = null;
    private static final String API_INFO_LICENCE_URI = null;
    private static final String API_INFO_LICENCE = null;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.contact.management.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(API_INFO_TITLE, API_INFO_DESCRIPTION, API_INFO_VERSION, API_INFO_TERMS_AND_SERVICE_URI, API_INFO_CONTACT, API_INFO_LICENCE_URI, API_INFO_LICENCE, Collections.emptyList());
    }
}
