package com.service.stocksearchanddisplayservice;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;

@Component
@EnableSwagger2
public class SwaggerConfigFile {
    
    private static final String BASE_PACKAGE = "com.service.stocksearchanddisplayservice.controllers";
    private static final String ERRORS = "/error.*";
    private static final String CONFIGURATIONS = "/configurations.*";
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Patient Copay Rebate APIs")
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .paths(Predicates.not(PathSelectors.regex(ERRORS)))
                .paths(Predicates.not(PathSelectors.regex(CONFIGURATIONS)))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Magic Copay Patient Rebate Portal API Specification",
                "Spring REST APIs",
                "",
                "",
                null,
                "License of API",
                "API License URL",
                Collections.emptyList()
        );
    }
}