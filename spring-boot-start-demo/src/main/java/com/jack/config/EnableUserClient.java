package com.jack.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 使用注解开启自定义Starter自动构建
 * 很多时候我们不想在引入Starter包时就执行初始化的逻辑，而是想要由用户来指定是否要开启Starter包的自动配置
 * 功能，比如常用的@EnableAsync这个注解就是用于开启调用方法异步执行的功能
 *
 * PS：如果使用了定义的此注解方式，就不需要在resource/META-INF目录下编写spring.factories文件了
 *
 * 使用时，直接在引入包的项目的启动类上面加@EnableUserClient注解就可以了
 *
 */
@Target(ElementType.TYPE)/*这个注解的使用范围 TYPE指的是类*/
@Retention(RetentionPolicy.RUNTIME) /*这个注解的生存周期，RUNTIME指运行级别保留*/
@Documented /* 指明修饰的注解，可以被例如javadoc此类的工具文档化，只负责标记，没有成员取值 */
@Inherited /* 允许子类继承父类中的注解 */
@Import({UserAutoConfigure.class}) /* 通过导入的方式实现把UserAutoConfigure实现加入SpringIOC容器中，这样就可以自动装配了 */
public @interface EnableUserClient {

}
