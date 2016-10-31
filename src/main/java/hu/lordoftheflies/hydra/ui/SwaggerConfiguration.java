/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.lordoftheflies.hydra.ui;

import com.fasterxml.classmate.TypeResolver;
import hu.lordoftheflies.hydra.ui.services.ApplicationController;
import java.lang.reflect.WildcardType;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import static springfox.documentation.schema.AlternateTypeRules.newRule;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author lordoftheflies
 */
@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = {
    ApplicationController.class
})
public class SwaggerConfiguration {

    @Bean
    public Docket petApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .apiInfo(apiInfo())
                //                .securitySchemes(newArrayList(apiKey()))
                //                .securityContexts(newArrayList(securityContext()))
                .directModelSubstitute(
                        LocalDate.class,
                        String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(true)
                .enableUrlTemplating(false);
    }

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration("validatorUrl");
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Hydra-UI API",
                "User interface API",
                "1.0",
                "http://lordoftheflies.github.io/wonderjam/termsOfServices.txt",
                new Contact("lordoftheflies", "https://github.com/lordoftheflies", "heglas11@gmail.com"),
                "License",
                "http://lordoftheflies.github.io/wonderjam/LICENSE.txt");
        return apiInfo;
    }

//    private ApiKey apiKey() {
//        return new ApiKey("mykey", "api_key", "header");
//    }
//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .forPaths(PathSelectors.any())
//                .build();
//    }
//    List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return newArrayList(new SecurityReference("mykey", authorizationScopes));
//    }
}
