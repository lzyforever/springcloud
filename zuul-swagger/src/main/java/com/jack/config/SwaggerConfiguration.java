package com.jack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


/**
 * Swagger2的配置类
 * 这里也需要添加Swagger2的注解@EnableSwagger2
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private final RouteLocator routeLocator;

    @Autowired
    public SwaggerConfiguration(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo());
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder().title("测试spring cloud swagger 2")
                .description("spring cloud 整合 swagger2 RESTful APIs")
                .termsOfServiceUrl("http://www.jack.com")
                .contact(new Contact("luozy", "lzyforever2008@sina.com", "lzyforever2008@sina.com")).version("1.0")
                .build();
    }

    /**
     * 第一种配置方式
     */
    @Bean
    @Primary
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            List<SwaggerResource> resources = new ArrayList<>();
            routeLocator.getRoutes().forEach(route -> {
                resources.add(createResource(route.getId(), route.getFullPath()));
            });
            return resources;
        };
    }

    private SwaggerResource createResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location.replace("**", "v2/api-docs"));
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
