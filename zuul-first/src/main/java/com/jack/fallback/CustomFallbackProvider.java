package com.jack.fallback;

import com.jack.jdbc.utils.JsonUtils;
import com.jack.vo.ResponseCode;
import com.jack.vo.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 回退机制
 * 实现回退机制需要实现Zuul的FallbackProvider接口
 * 在SpringCloud中，Zuul默认整合了Hystrix，当后端服务异常时可以为Zuul添加回退功能，返回默认的数据给客户端。
 */
@Component
public class CustomFallbackProvider implements FallbackProvider {

    private Logger logger = LoggerFactory.getLogger(CustomFallbackProvider.class);

    /**
     * getRoute返回*表示对所有服务进行回退操作，如果只想对某个服务进行回退，那么
     * 就需要回退的服务名称，这个名称一定要是注册到Eureka中的名称
     */
    @Override
    public String getRoute() {
        return "*";
    }

    /**
     * 通过ClientHttpResponse构造回退的内容
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            /**
             * 通过getStatusCode返回响应的状态码
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return this.getStatusCode().value();
            }

            /**
             * 通过getStatusText返回响应状态码对应的文本
             */
            @Override
            public String getStatusText() throws IOException {
                return this.getStatusCode().getReasonPhrase();
            }

            @Override
            public void close() {
            }

            /**
             * 通过getBody返回回退的内容
             */
            @Override
            public InputStream getBody() throws IOException {
                if (cause != null) {
                    logger.error("", cause.getCause());
                }
                ResponseData data = ResponseData.fail(route + " 服务器内部错误 ", ResponseCode.SERVER_ERROR_CODE.getCode());
                return new ByteArrayInputStream(JsonUtils.toJson(data).getBytes());
            }

            /**
             * 通过 getHeaders返回响应的请求头信息
             */
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                MediaType mt = new MediaType("application", "json", Charset.forName("UTF-8"));
                headers.setContentType(mt);
                return headers;
            }
        };
    }
}
