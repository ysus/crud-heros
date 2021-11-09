package com.jmora.web.app.configuration;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any()) 
          .build()
          .globalRequestParameters(globalRequestParameters())
          .apiInfo(apiInfo());
    }
    
    private List<RequestParameter> globalRequestParameters() {
        
        RequestParameterBuilder parameterBuilder = new RequestParameterBuilder()
        		.in(ParameterType.HEADER)
        		.name("Authorization")
        		.description("Authorization header")
        		.required(false)
        		.query(param -> param.model(model -> model.scalarModel(ScalarType.STRING)));
        return Collections.singletonList(parameterBuilder.build());
    }

    private ApiInfo apiInfo() {
    
        return new ApiInfoBuilder()
                .title("Hero Api")
                .description("Crud Rest Api")
                .termsOfServiceUrl("http://127.0.0.1:8100/")
                .version("1.0.0")
                .build();
    }
}