package com.example.dispatch.config;

import java.util.Arrays;
import java.util.List;


import com.google.common.collect.Lists;
import com.example.dispatch.response.ErrorResponse;
import com.example.dispatch.response.Response;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket api() { 
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.select()
            .apis(RequestHandlerSelectors
                .basePackage("com.example.dispatch.controller"))
            .paths(PathSelectors.regex("/.*"))      
            .build()
            .securityContexts(Lists.newArrayList(securityContext()))
            .securitySchemes(Lists.newArrayList(apiKey()));

        ModelRef errorModel = new ModelRef("ErrorResponse");
        List<ResponseMessage> responseMessages = Arrays.asList(
            new ResponseMessageBuilder().code(400).message("Client sent an invalid request").responseModel(errorModel).build(),
            new ResponseMessageBuilder().code(401).message("Client are not authorized to view the resource").build(),
            new ResponseMessageBuilder().code(404).message("The resource you were trying to reach is not found").build(),
            new ResponseMessageBuilder().code(500).message("Internal Server Error — A generic error occurred on the server").build());

        docket.additionalModels(typeResolver.resolve(Response.class),
                                typeResolver.resolve(ErrorResponse.class))
            .globalResponseMessage(RequestMethod.POST, responseMessages)
            .globalResponseMessage(RequestMethod.PUT, responseMessages)
            .globalResponseMessage(RequestMethod.GET, responseMessages)
            .globalResponseMessage(RequestMethod.DELETE, responseMessages);

        return docket;
    }

    @Bean
    SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex("(?!/admin/login)(?!/admin/signup).+"))
            .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
            = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
            new SecurityReference("JWT", authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
}