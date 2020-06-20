package com.jack.encrypt.core;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 基于Servlet来实现响应内容的加密包装器
 */
public class EncryptionResponseWrapper extends HttpServletResponseWrapper {

    private ServletOutputStream sos;

    private ByteArrayOutputStream baos;

    public EncryptionResponseWrapper(HttpServletResponse response) {
        super(response);
        baos = new ByteArrayOutputStream();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (null == sos) {
            sos = new ServletOutputStream() {
                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setWriteListener(WriteListener writeListener) {

                }

                @Override
                public void write(int b) throws IOException {
                    baos.write(b);
                }
            };
        }
        return sos;
    }

    public String getResponseData() {
        return baos.toString();
    }
}
