package org.zipper.helper.web.signature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zipper.helper.exception.ErrorCode;
import org.zipper.helper.exception.HelperException;

import javax.servlet.http.HttpServletRequest;

/**
 * 签名请求头
 *
 * @author zhuxj
 * @since 2020/08/24
 */
public class SignatureHeader {

    private static final Logger logger = LoggerFactory.getLogger(SignatureHeader.class);

    public SignatureHeader(HttpServletRequest request) {
        if (request != null) {
            this.appId = request.getHeader("X-appId");
            this.sign = request.getHeader("X-sign");
            this.timestamp = request.getHeader("X-timestamp");
        }
        if (null == appId || "".equals(appId)) {
            throw HelperException.newException(ErrorCode.SIGNATURE_ERROR, "缺失X-appId");
        }

        if (null == sign || "".equals(sign)) {
            throw HelperException.newException(ErrorCode.SIGNATURE_ERROR, "缺失X-sign");
        }
        if (null == timestamp) {
            throw HelperException.newException(ErrorCode.SIGNATURE_ERROR, "缺失X-timestamp");
        }
        try {
            getTimestampLong();
        } catch (NumberFormatException e) {
            logger.error("时间戳X-timestamp格式有误", e);
            throw HelperException.newException(ErrorCode.SIGNATURE_ERROR, "X-timestamp格式有误");
        }
    }


    /**
     * 分配给第三方的应用ID
     */
    private String appId;

    /**
     * 请求时间戳，用于确认请求时效
     */
    private String timestamp;
    /**
     * 签名结果
     */
    private String sign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public long getTimestampLong() {
        return Long.parseLong(this.timestamp);
    }
}
