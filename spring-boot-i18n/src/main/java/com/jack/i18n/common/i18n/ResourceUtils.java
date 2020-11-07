package com.jack.i18n.common.i18n;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Objects;


/**
 * 国际化资源工具类
 * 资源读取国际化资源文件
 */
@Slf4j
public class ResourceUtils {

    /**
     * spring resources
     */
    private static final ReloadableResourceBundleMessageSource MESSAGE_SOURCE = new ReloadableResourceBundleMessageSource();

    static {
        // 创建文件的规则 message_**_**.properties 请根据国际化文件创建，第一个**为语言，第二个**为地区
        // 指定国际化资源文件路径
        MESSAGE_SOURCE.setBasename("i18n/message");
        // 指定将用来加载对应资源文件时使用的编码，默认为空，表示将使用默认的编码进行获取。
        MESSAGE_SOURCE.setDefaultEncoding("UTF-8");
        // 是否允许并发刷新
        MESSAGE_SOURCE.setConcurrentRefresh(true);
        // ReloadableResourceBundleMessageSource也是支持缓存对应的资源文件的，默认的缓存时间为永久，即获取了一次资源文件后就将其缓存起来，以后再也不重新去获取该文件。这个可以通过setCacheSeconds()方法来指定对应的缓存时间，单位为秒
        MESSAGE_SOURCE.setCacheSeconds(1200);
    }


    /**
     * 获取中文的数据
     */
    public static String getChineseValueByKey(String key) {
        try {
            return MESSAGE_SOURCE.getMessage(key, null, Locale.CHINA);
        } catch (NoSuchMessageException e) {
            return "";
        }
    }

    /**
     * 获取英文的数据
     */
    public static String getEnglishValueByKey(String key) {
        try {
            return MESSAGE_SOURCE.getMessage(key, null, Locale.US);
        } catch (NoSuchMessageException e) {
            return "";
        }
    }

    /**
     * 通过传入的lang来决定返回中文还是英文
     */
    public static String getEnvValueByKey(String key) {
        try {
            Locale lang = getLanguage();
            return MESSAGE_SOURCE.getMessage(key, null, lang);
        } catch (NoSuchMessageException e) {
            return "";
        }
    }

    /**
     * 获取locale的数据
     */
    public static String getLocaleByKey(String key, Locale locale) {
        return MESSAGE_SOURCE.getMessage(key, null, locale);
    }

    /**
     *  通过header中的lang来获取本地语言
     */
    public static Locale getLanguage() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            return Locale.CHINA;
        } else {
            HttpServletRequest request = requestAttributes.getRequest();
            String lang = request.getHeader("lang");
            return Locale.US.getLanguage().equals(lang) ? Locale.US : Locale.CHINA;
        }
    }
}
