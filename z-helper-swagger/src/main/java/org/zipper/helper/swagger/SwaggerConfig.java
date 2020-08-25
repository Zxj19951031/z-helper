/*
 * Copyright (c) 2019/4/3 9:49:54
 * Created by zhuxj
 */

package org.zipper.helper.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2.0 配置.
 * 程序通过扫描application.yml（.properties）进行配置加载.
 * 所需参数有
 * swagger2.apiInfo.title  默认 Title
 * swagger2.apiInfo.version 默认 1.0
 * swagger2.apiInfo.description 默认 无
 * swagger2.apiInfo.contact.name 默认 Developer
 * swagger2.apiInfo.contact.url 默认 http://127.0.0.1
 * swagger2.apiInfo.contact.email 默认 无
 * swagger2.apis.basePackage 默认 *
 *
 * @author zhuxj
 */
@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "swagger2")
public class SwaggerConfig {

//    @Value("${swagger2.apiInfo.title:Title}")
//    private String title;
//    @Value("${swagger2.apiInfo.version:1.0}")
//    private String version;
//    @Value("${swagger2.apiInfo.description:}")
//    private String description;
//    @Value("${swagger2.apiInfo.contact.name:Developer}")
//    private String name;
//    @Value("${swagger2.apiInfo.contact.url:http://127.0.0.1}")
//    private String url;
//    @Value("${swagger2.apiInfo.contact.email:}")
//    private String email;
//    @Value("${swagger2.apis.basePackage:*}")
//    private String basePackage;

    private ApiInfo apiInfo;
    private Apis apis;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title(apiInfo.getTitle())
                        .version(apiInfo.getVersion())
                        .description(apiInfo.getDescription())
                        .contact(new Contact(
                                apiInfo.getContact().getName(),
                                apiInfo.getContact().getUrl(),
                                apiInfo.getContact().getEmail()))
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage(apis.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(ApiInfo apiInfo) {
        this.apiInfo = apiInfo;
    }

    public Apis getApis() {
        return apis;
    }

    public void setApis(Apis apis) {
        this.apis = apis;
    }
}

class ApiInfo {
    private String title;
    private String version;
    private String description;
    private Contact2 contact;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Contact2 getContact() {
        return contact;
    }

    public void setContact(Contact2 contact) {
        this.contact = contact;
    }
}

class Contact2 {
    private String name;
    private String url;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

class Apis {
    private String basePackage;

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
