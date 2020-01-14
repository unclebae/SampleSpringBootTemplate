package com.template.coe.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
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
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
//        return new ApiInfo(
//                "Rest API TeSt",            // title
//                "Api Description....",  // description
//                "API v2",  // version
//                "Test API Service", // termsOfServiceUrl
//                new Contact("KIDO", "www.test.com", "baekido@gamil.com"), // author's (name, url, email)
//                "License",  // license
//                "API license URL",  // licenseUrl
//                Collections.EMPTY_LIST  // vendorExtensions
//        );
        return new ApiInfoBuilder()
                .title("Rest API Test")
                .description("Api Description...")
                .version("API v2")
                .termsOfServiceUrl("Test API Service")
                .contact(new Contact("KIDO", "www.test.com", "baekido@gmail.com"))
                .license("Apache")
                .licenseUrl("http://...")
                .build();
    }

}
