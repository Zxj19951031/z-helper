package org.zipper.helper.web.signature;


import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.zipper.helper.exception.ErrorCode;
import org.zipper.helper.exception.HelperException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * 抽象切面处理签名逻辑
 *
 * @author zhuxj
 * @since 2020/08/24
 */
public abstract class SignatureHandler {

    private final static Logger logger = LoggerFactory.getLogger(SignatureHandler.class);

    /**
     * 请求超时时间 15min
     */
    private long maxRequestTimeout = 15 * 60 * 1000;

    @Pointcut("@annotation(org.zipper.helper.web.signature.Signature)")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        SignatureHeader signHeader = new SignatureHeader(req);
        AbstractThirdAppInfo appInfo = getAppInfo(signHeader.getAppId());

        if (appInfo == null) {
            logger.info("未知第三方应用尝试访问，appId:{}", signHeader.getAppId());
            throw HelperException.newException(ErrorCode.SIGNATURE_ERROR, "未知应用");
        }
        if (System.currentTimeMillis() - signHeader.getTimestampLong() > maxRequestTimeout) {
            logger.info("应用:{}请求超时,请求时间:{}", appInfo.getAppName(), signHeader.getTimestamp());
            throw HelperException.newException(ErrorCode.SIGNATURE_ERROR, "请求超时，请重试");
        }
        try {
            sign(req, appInfo, signHeader);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw HelperException.newException(ErrorCode.SIGNATURE_ERROR, "IO异常，联系管理员");
        }
    }

    private void sign(HttpServletRequest request, AbstractThirdAppInfo appInfo, SignatureHeader signatureHeader) throws IOException {

        StringBuilder sb = new StringBuilder();

        //获取body（对应@RequestBody）
        if (request instanceof RepeatInputStreamRequestWrapper) {
            String body = new String(((RepeatInputStreamRequestWrapper) request).getBody());
            if (StringUtils.isNotBlank(body)) {
                sb.append(body).append('#');
            }
        }

        //获取parameters（对应@RequestParam）
        if (!CollectionUtils.isEmpty(request.getParameterMap())) {
            Map<String, String[]> params = request.getParameterMap();
            if (!CollectionUtils.isEmpty(params)) {
                params.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByKey())
                        .forEach(paramEntry -> {
                            String paramValue = String.join(",", Arrays.stream(paramEntry.getValue()).sorted().toArray(String[]::new));
                            sb.append(paramEntry.getKey()).append("=").append(paramValue).append('#');
                        });
            }
        }

        //获取path variable（对应@PathVariable）
        ServletWebRequest webRequest = new ServletWebRequest(request, null);
        Map<String, String> uriTemplateVars = (Map<String, String>) webRequest.getAttribute(
                HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        if (!CollectionUtils.isEmpty(uriTemplateVars)) {
            String[] paths = uriTemplateVars.values().toArray(new String[]{});
            if (ArrayUtils.isNotEmpty(paths)) {
                String pathValues = String.join(",", Arrays.stream(paths).sorted().toArray(String[]::new));
                sb.append(pathValues);
            }
        }

        String newSign = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, appInfo.getAppSecret()).hmacHex(sb.toString());

        if (!newSign.equals(signatureHeader.getSign())) {
            throw HelperException.newException(ErrorCode.SIGNATURE_ERROR, "签名认证失败");
        }
    }

    public void setMaxRequestTimeout(long maxRequestTimeout) {
        this.maxRequestTimeout = maxRequestTimeout;
    }

    public abstract AbstractThirdAppInfo getAppInfo(String appId);
}
