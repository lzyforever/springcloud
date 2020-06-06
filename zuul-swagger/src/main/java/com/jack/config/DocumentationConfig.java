//package com.jack.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Component;
//import springfox.documentation.swagger.web.SwaggerResource;
//import springfox.documentation.swagger.web.SwaggerResourcesProvider;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 第二种配置方式（推荐使用）
// * 通过获取Eureka中的服务信息来生成Swagger API
// */
//@EnableSwagger2
//@Component
//@Primary
//public class DocumentationConfig implements SwaggerResourcesProvider {
//
//    @Autowired
//    private DiscoveryClient discoveryClient;
//
//    @Value("${spring.application.name}")
//    private String applicationName;
//
//    @Override
//    public List<SwaggerResource> get() {
//        List<SwaggerResource> resources = new ArrayList<>();
//        // 排除自身，将其他的服务添加进去
//        discoveryClient.getServices().stream().filter(s -> !s.equals(applicationName)).forEach(name -> {
//            resources.add(swaggerResource(name, "/" + name + "/v2/api-docs", "2.0"));
//        });
//        return resources;
//    }
//
//    private SwaggerResource swaggerResource(String name, String location, String version) {
//        SwaggerResource swaggerResource = new SwaggerResource();
//        swaggerResource.setName(name);
//        swaggerResource.setLocation(location);
//        swaggerResource.setSwaggerVersion(version);
//        return swaggerResource;
//    }
//}
