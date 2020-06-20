package com.jack.mvc.filter;

import com.jack.encrypt.core.EncryptionConfig;
import com.jack.encrypt.core.EncryptionFilter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Arrays;

/**
 * 定义一个Filter来设置哪些URL需要过滤，并设置AES的加解密KEY
 */
public class ApiEncryptionFilter implements Filter {

    EncryptionFilter filter = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        EncryptionConfig config = new EncryptionConfig();
        config.setKey("031d46bb084f4c729a09b61637d0a5c8");
        config.setRequestDecryptUriList(Arrays.asList("/save"));
        config.setResponseEncryptUriList(Arrays.asList("/encryptEntity"));
        filter = new EncryptionFilter(config);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filter.doFilter(servletRequest, servletResponse, filterChain);
    }

    @Override
    public void destroy() {

    }
}
