package com.jack.encrypt.core;

import com.jack.encrypt.algorithm.AesEncryptAglorithm;
import com.jack.encrypt.algorithm.EncryptAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据加解密过滤器
 */
public class EncryptionFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(EncryptionFilter.class);

    private EncryptionConfig encryptionConfig;

    private EncryptAlgorithm encryptAlgorithm = new AesEncryptAglorithm();

    public EncryptionFilter() {
        this.encryptionConfig = new EncryptionConfig();
    }

    public EncryptionFilter(EncryptionConfig encryptionConfig) {
        this.encryptionConfig = encryptionConfig;
    }

    public EncryptionFilter(EncryptionConfig encryptionConfig, EncryptAlgorithm encryptAlgorithm) {
        this.encryptionConfig = encryptionConfig;
        this.encryptAlgorithm = encryptAlgorithm;
    }

    public EncryptionFilter(String key) {
        EncryptionConfig encryptionConfig = new EncryptionConfig();
        encryptionConfig.setKey(key);
        this.encryptionConfig = encryptionConfig;
    }

    public EncryptionFilter(String key, List<String> responseEncryptUriList, List<String> requestDecryptUriList, String responseCharset, boolean debug) {
        this.encryptionConfig = new EncryptionConfig(key, responseEncryptUriList, requestDecryptUriList, responseCharset, debug);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        logger.debug("RequestURI：{}", uri);

        // 调试模式不加密
        if (encryptionConfig.isDebug()) {
            chain.doFilter(request, response);
            return;
        }

        boolean decryptionStatus = this.contains(encryptionConfig.getRequestDecryptUriList(), uri, req.getMethod());
        boolean encryptionStatus = this.contains(encryptionConfig.getResponseEncryptUriList(), uri, req.getMethod());
        boolean decryptionIgnoreStatus = this.contains(encryptionConfig.getRequestDecryptUriIgnoreList(), uri, req.getMethod());
        boolean encryptionIgnorestatus = this.contains(encryptionConfig.getResponseEncryptUriIgnoreList(), uri, req.getMethod());

        // 没有配置具体加解密的URI默认全部都开启加解密
        if (CollectionUtils.isEmpty(encryptionConfig.getRequestDecryptUriList()) && CollectionUtils.isEmpty(encryptionConfig.getResponseEncryptUriList())) {
            decryptionStatus = true;
            encryptionStatus = true;
        }

        // 接口在忽略加密列表中
        if (encryptionIgnorestatus) {
            encryptionStatus = false;
        }

        if (decryptionIgnoreStatus) {
            decryptionStatus = false;
        }

        // 没有加解密操作
        if (!decryptionStatus && !encryptionStatus) {
            chain.doFilter(request, response);
            return;
        }

        EncryptionRequestWrapper requestWrapper = null;
        EncryptionResponseWrapper responseWrapper = null;

        //配置了需要解密才处理
        if (decryptionStatus) {
            requestWrapper = new EncryptionRequestWrapper(req);
            processDecryption(requestWrapper, req);
        }

        if (encryptionStatus) {
            responseWrapper = new EncryptionResponseWrapper(rep);
        }

        // 同时需要加解密
        if (encryptionStatus && decryptionStatus) {
            chain.doFilter(requestWrapper, responseWrapper);
        } else if (encryptionStatus) {
            // 只需要响应加密
            chain.doFilter(request, responseWrapper);
        } else if (decryptionStatus) {
            // 只需要请求解密
            chain.doFilter(requestWrapper, response);
        }

        // 配置了需要加密才处理输出加密内容
        if (encryptionStatus) {
            String responseData = responseWrapper.getResponseData();
            writeEncryptContent(responseData, response);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean contains(List<String> list, String uri, String methodType) {
        if (list.contains(uri)) {
            return true;
        }
        String prefixUri = methodType.toLowerCase() + ":" + uri;
        logger.debug("contains uri：{}", prefixUri);

        return list.contains(prefixUri);
    }

    /**
     * 请求解密处理
     *
     * @param requestWrapper 请求包装器
     * @param req            请求对象
     */
    private void processDecryption(EncryptionRequestWrapper requestWrapper, HttpServletRequest req) {
        String requestData = requestWrapper.getRequestData();
        String uri = req.getRequestURI();
        logger.debug("RequestData：{}", requestData);
        try {
            if (!StringUtils.endsWithIgnoreCase(req.getMethod(), RequestMethod.GET.name())) {
                String decryptRequestData = encryptAlgorithm.decrypt(requestData, encryptionConfig.getKey());
                logger.debug("DecryptRequestData：{}", decryptRequestData);
                requestWrapper.setRequestData(decryptRequestData);
            }

            // URL参数解密
            Map<String, String> paramsMap = new HashMap<>();
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String prefixUri = req.getMethod().toLowerCase() + ":" + uri;
                if (encryptionConfig.getRequestDecryptParams(prefixUri).contains(paramName)) {
                    String paramValue = req.getParameter(paramName);
                    String decryptParamValue = encryptAlgorithm.decrypt(paramValue, encryptionConfig.getKey());
                    paramsMap.put(paramName, decryptParamValue);
                }
            }
            requestWrapper.setParamsMap(paramsMap);
        } catch (Exception e) {
            logger.error("请求数据解密失败", e);
            throw new RuntimeException("请求数据解密失败", e);
        }
    }

    /**
     * 输出加密内容
     *
     * @param responseData 加密的内容
     * @param response     响应对象
     */
    private void writeEncryptContent(String responseData, ServletResponse response) throws IOException {
        logger.debug("ResponseData：" + responseData);
        ServletOutputStream sos = null;
        try {
            responseData = encryptAlgorithm.encrypt(responseData, encryptionConfig.getKey());
            logger.debug("EncryptResponseData：{}", responseData);
            response.setContentLength(responseData.length());
            response.setCharacterEncoding(encryptionConfig.getResponseCharset());
            sos = response.getOutputStream();
            sos.write(responseData.getBytes(encryptionConfig.getResponseCharset()));
        } catch (Exception e) {
            logger.error("响应数据加密失败", e);
            throw new RuntimeException("响应数据加密失败", e);
        } finally {
            if (null != sos) {
                sos.flush();
                sos.close();
            }
        }
    }
}
