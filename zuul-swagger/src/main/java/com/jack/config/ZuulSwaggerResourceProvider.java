//package com.jack.config;
//
//import com.google.common.collect.Lists;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Component;
//import springfox.documentation.swagger.web.SwaggerResource;
//import springfox.documentation.swagger.web.SwaggerResourcesProvider;
//
//import java.util.List;
//
///**
// * 第二种配置方式（推荐使用）
// */
//@Component
//@Primary
//public class ZuulSwaggerResourceProvider implements SwaggerResourcesProvider {
//
//    private final RouteLocator routeLocator;
//
//    @Autowired
//    public ZuulSwaggerResourceProvider(RouteLocator routeLocator) {
//        this.routeLocator = routeLocator;
//    }
//
//    @Override
//    public List<SwaggerResource> get() {
//        List<SwaggerResource> resources = Lists.newArrayList();
//        routeLocator.getRoutes().forEach(route -> {
//            resources.add(createResource(route.getId(), route.getFullPath().replace("**", "v2/api-docs")));
//        });
//        return resources;
//    }
//
//    private SwaggerResource createResource(String name, String location) {
//        SwaggerResource swaggerResource = new SwaggerResource();
//        swaggerResource.setName(name);
//        swaggerResource.setLocation(location);
//        swaggerResource.setSwaggerVersion("2.0");
//        return swaggerResource;
//    }
//}
