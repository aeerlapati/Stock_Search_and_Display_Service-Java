package com.service.stocksearchanddisplayservice;

import javax.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfigFile 
{
    
    private static final String BASE_PACKAGE = "com.service.stocksearchanddisplayservice.controllers";
    private static final String ERRORS = "/error.*";
    private static final String CONFIGURATIONS = "/configurations.*";
    private static final String DESCRIPTION = "API documentation for Stock Search & Display Service. Service-Reference-URL: http://stockssearchanddisplay.us-west-1.elasticbeanstalk.com/";
    private static final String LICENSE = "Developed by Abhinav Eerlapati";
    private static final String VERSION = "1.0.0";
    private static final String TITLE = "Stock Search Display Service";
    
    private ApiInfo apiInfo() 
    {
        return new ApiInfoBuilder().title(TITLE).description(DESCRIPTION).license(LICENSE).version(VERSION).build();
    }
    
    @Bean
    public Docket api(ServletContext servletContext) 
    {
    	//.useDefaultResponseMessages(false)
    	//.apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(TITLE)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api/v1/get.*"))
                .paths(Predicates.not(PathSelectors.regex("/api/v1/getSimulatedPrice")))
				 //.paths(PathSelectors.any())
				/* * .paths(Predicates.not(PathSelectors.regex(ERRORS)))
				 * .paths(Predicates.not(PathSelectors.regex(CONFIGURATIONS)))
				 */
                .build();
    }
}