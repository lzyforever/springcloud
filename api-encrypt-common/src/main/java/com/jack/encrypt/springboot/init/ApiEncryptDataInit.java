package com.jack.encrypt.springboot.init;

import com.jack.encrypt.springboot.HttpMethodTypePrefixConstant;
import com.jack.encrypt.springboot.annotation.Decrypt;
import com.jack.encrypt.springboot.annotation.DecryptIgnore;
import com.jack.encrypt.springboot.annotation.Encrypt;
import com.jack.encrypt.springboot.annotation.EncryptIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 初始化API加密对应的数据
 */
public class ApiEncryptDataInit implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(ApiEncryptDataInit.class);

    /**
     * 需要对响应内容进行加密的接口URI
     * 比如：/user/list
     * 不支持@PathVariable格式的URI
     */
    public static List<String> responseEncryptUriList = new ArrayList<>();

    /**
     * 需要对请求内容进行解密的接口URI
     * 比如：/user/list
     * 不支持@PathVariable格式的URI
     */
    public static List<String> requestDecryptUriList = new ArrayList<>();

    /**
     * 忽略对响应内容进行加密的接口URI
     * 比如：/user/list
     * 不支持@PathVariable格式的URI
     */
    public static List<String> responseEncryptUriIgnoreList = new ArrayList<>();

    /**
     * 忽略对请求内容进行加密的接口URI
     * 比如：/user/list
     * 不支持@PathVariable格式的URI
     */
    public static List<String> requestDecryptUriIgnoreList = new ArrayList<>();

    /**
     * URL参数需要解密的配置
     * 比如：/user/list?name=加密内容
     * 格式：Key为API路径，Value为需要解密的字段
     * 示例：/user/list [name,age]
     */
    public static Map<String, List<String>> requestDecryptParamMap = new HashMap<>();

    private String contextPath;

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.contextPath = ctx.getEnvironment().getProperty("server.servlet.context-path");
        Map<String, Object> beanMap = ctx.getBeansWithAnnotation(Controller.class);
        initData(beanMap);
        initRequestDecryptParam(ctx.getEnvironment());
    }

    /**
     * 初始化数据
     *
     * @param beanMap
     */
    private void initData(Map<String, Object> beanMap) {
        if (null != beanMap) {
            for (Object bean : beanMap.values()) {
                Class<?> clz = bean.getClass();
                Method[] methods = clz.getMethods();
                for (Method method : methods) {
                    Encrypt encrypt = AnnotationUtils.findAnnotation(method, Encrypt.class);
                    if (null != encrypt) {
                        // 注解中的URI优先级最高
                        String uri = encrypt.value();
                        if (!StringUtils.hasText(uri)) {
                            uri = getApiUri(clz, method);
                        }
                        logger.debug("Encrypt URI：{}", uri);
                        responseEncryptUriList.add(uri);
                    }
                    Decrypt decrypt = AnnotationUtils.findAnnotation(method, Decrypt.class);
                    if (null != decrypt) {
                        // 注解中的URI优先级最高
                        String uri = decrypt.value();
                        if (!StringUtils.hasText(uri)) {
                            uri = getApiUri(clz, method);
                        }
                        String decryptParam = decrypt.decryptParam();
                        if (StringUtils.hasText(decryptParam)) {
                            requestDecryptParamMap.put(uri, Arrays.asList(decryptParam.split(",")));
                        }
                        logger.debug("Decrypt URI：{}", uri);
                        requestDecryptUriList.add(uri);
                    }

                    EncryptIgnore encryptIgnore = AnnotationUtils.getAnnotation(method, EncryptIgnore.class);
                    if (null != encryptIgnore) {
                        // 注解中的URI优先级最高
                        String uri = encryptIgnore.value();
                        if (!StringUtils.hasText(uri)) {
                            uri = getApiUri(clz, method);
                        }
                        logger.debug("EncryptIgnore URI：{}", uri);
                        responseEncryptUriIgnoreList.add(uri);
                    }

                    DecryptIgnore decryptIgnore = AnnotationUtils.getAnnotation(method, DecryptIgnore.class);
                    if (null != decryptIgnore) {
                        // 注解中的URI优先级最高
                        String uri = decryptIgnore.value();
                        if (!StringUtils.hasText(uri)) {
                            uri = getApiUri(clz, method);
                        }
                        logger.debug("DecryptIgnore URI：{}", uri);
                        requestDecryptUriIgnoreList.add(uri);
                    }
                }
            }
        }
    }

    /**
     * 获取方法上注解参数中的URI
     *
     * @param clz
     * @param method
     * @return
     */
    private String getApiUri(Class<?> clz, Method method) {
        String methodType = "";
        StringBuilder uri = new StringBuilder();

        RequestMapping reqMapping = AnnotationUtils.findAnnotation(clz, RequestMapping.class);
        if (null != reqMapping) {
            // 获取接口类上@RequestMapping中的值
            uri.append(formatUri(reqMapping.value()[0]));
        }

        GetMapping getMapping = AnnotationUtils.getAnnotation(method, GetMapping.class);
        PostMapping postMapping = AnnotationUtils.getAnnotation(method, PostMapping.class);
        PutMapping putMapping = AnnotationUtils.getAnnotation(method, PutMapping.class);
        DeleteMapping deleteMapping = AnnotationUtils.getAnnotation(method, DeleteMapping.class);
        RequestMapping requestMapping = AnnotationUtils.getAnnotation(method, RequestMapping.class);

        if (null != getMapping) {
            methodType = HttpMethodTypePrefixConstant.GET;
            uri.append(formatUri(getMapping.value()[0]));
        } else if (null != postMapping) {
            methodType = HttpMethodTypePrefixConstant.POST;
            uri.append(formatUri(postMapping.value()[0]));
        } else if (null != putMapping) {
            methodType = HttpMethodTypePrefixConstant.PUT;
            uri.append(formatUri(putMapping.value()[0]));
        } else if (null != deleteMapping) {
            methodType = HttpMethodTypePrefixConstant.DELETE;
            uri.append(formatUri(deleteMapping.value()[0]));
        } else if (null != requestMapping) {
            RequestMethod requestMethod = RequestMethod.GET;
            if (requestMapping.method().length > 0) {
                requestMethod = requestMapping.method()[0];
            }
            methodType = requestMethod.name().toLowerCase() + ":";
            uri.append(formatUri(requestMapping.value()[0]));
        }

        if (StringUtils.hasText(this.contextPath) && !"/".equals(this.contextPath)) {
            if (this.contextPath.endsWith("/")) {
                this.contextPath = this.contextPath.substring(0, this.contextPath.length() - 1);
            }
            return methodType + this.contextPath + uri.toString();
        }

        return methodType + uri.toString();
    }

    /**
     * 格式化URI，如果URI前面没有/就加上
     *
     * @param uri
     * @return
     */
    private String formatUri(String uri) {
        if (uri.startsWith("/")) {
            return uri;
        }
        return "/" + uri;
    }

    /**
     * 初始化请求参数
     *
     * @param environment 系统环境变量对象
     */
    private void initRequestDecryptParam(Environment environment) {
        for (Iterator it = ((AbstractEnvironment) environment).getPropertySources().iterator(); it.hasNext(); ) {
            PropertySource propertySource = (PropertySource) it.next();
            if (propertySource instanceof EnumerablePropertySource) {
                for (String name : ((EnumerablePropertySource) propertySource).getPropertyNames()) {
                    if (name.startsWith("spring.encrypt.requestDecryptParam")) {
                        String[] keys = name.split("\\.");
                        String key = keys[keys.length - 1];
                        String property = environment.getProperty(name);
                        requestDecryptParamMap.put(key.replace("$", ":"), Arrays.asList(property.split(",")));
                    }
                }
            }
        }
    }
}
