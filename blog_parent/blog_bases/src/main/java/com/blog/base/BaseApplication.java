package com.blog.base;

import com.google.common.base.Predicates;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName BaseApplication
 * @Description TODO
 * @Author zhangxiaoxiong
 * @Date 2018/11/26 23:07
 * @Version 1.0
 **/
@SpringBootApplication
@EnableSwagger2
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class,args);
    }

//    @Bean
//    public IdWorker idWorker(){
//        return new IdWorker();
//    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select() // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.any())// 对所有api进行监控
                //不显示错误的接口地址
                .paths(Predicates.not(PathSelectors.regex("/error.*")))//错误路径不监控
                .paths(PathSelectors.regex("/.*"))// 对根下所有路径进行监控
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("UserController")
                .description("User接口")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }

}
