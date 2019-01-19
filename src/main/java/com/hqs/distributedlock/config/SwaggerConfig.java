package com.hqs.distributedlock.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by huangqingshi on 2019/1/8.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.switch}")
    private boolean swaggerSwitch;

    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.enable(swaggerSwitch);
        docket
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hqs.distributedlock.controller"))
                .paths(PathSelectors.any()).build();
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring boot distributed lock")
                .description("测试")
                .contact(new Contact("黄青石","http://www.cnblogs.com/huangqingshi","68344150@qq.com"))
                .termsOfServiceUrl("http://www.cnblogs.com/huangqingshi")
                .version("1.0")
                .build();
    }
}
