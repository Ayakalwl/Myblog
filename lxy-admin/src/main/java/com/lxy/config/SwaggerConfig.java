package com.lxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lxy.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("团队名", "http://www.my.com", "my@my.com");
        return new ApiInfoBuilder()
                .title("后台接口")
                .description("文档描述")
                // 联系方式
                .contact(contact)
                // 版本
                .version("1.0.0")
                .build();
    }
}