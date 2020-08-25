package org.zipper.helper.web.signature;

import org.zipper.helper.web.entity.BaseEntity;

/**
 * 第三方应用配置信息
 *
 * @author zhuxj
 * @since 2020/08/24
 */
public abstract class AbstractThirdAppInfo extends BaseEntity {
    private String appName;
    private String appId;
    private String appSecret;
    private String regexUri;


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getRegexUri() {
        return regexUri;
    }

    public void setRegexUri(String regexUri) {
        this.regexUri = regexUri;
    }
}
