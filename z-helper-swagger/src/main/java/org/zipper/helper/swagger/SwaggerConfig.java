/*
 * Copyright (c) 2019/4/3 9:49:54
 * Created by zhuxj
 */

package org.zipper.helper.swagger;

import org.springframework.beans.factory.annotation.Value;
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
public class SwaggerConfig {

    @Value("${swagger2.apiInfo.title:Title}")
    private String title;
    @Value("${swagger2.apiInfo.version:1.0}")
    private String version;
    @Value("${swagger2.apiInfo.description:}")
    private String description;
    @Value("${swagger2.apiInfo.contact.name:Developer}")
    private String name;
    @Value("${swagger2.apiInfo.contact.url:http://127.0.0.1}")
    private String url;
    @Value("${swagger2.apiInfo.contact.email:}")
    private String email;
    @Value("${swagger2.apis.basePackage:*}")
    private String basePackage;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title(title)
                        .version(version)
                        .description(description)
                        .contact(new Contact(name, url, email))
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }
}
