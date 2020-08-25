package org.zipper.helper.web.signature;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器，用于在拦截器验签之前将HttpServletRequest重新包装，以实现复用
 */
@Component
@WebFilter(urlPatterns = "/api/signed/**", filterName = "signatureFilter")
public class SignatureRequestWrapperFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest && "application/json".equalsIgnoreCase(request.getContentType())) {
            try {
                requestWrapper = new RepeatInputStreamRequestWrapper((HttpServletRequest) request);
            } catch (Exception e) {
                requestWrapper = null;
            }
        }
        if (null == requestWrapper) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {

    }
}