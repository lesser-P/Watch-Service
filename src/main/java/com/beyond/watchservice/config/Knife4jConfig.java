package com.beyond.watchservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * Name: Knife4jConfig
 * Description: Swagger配置依赖
 * author: JJ
 * create: 2022/11/17 14:50
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {


    @Bean(value = "dockerBean")
    public Docket dockerBean() {

        List<Parameter> pars = new ArrayList<>();

        //构建器，参数构建器
        ParameterBuilder tokenPar = new ParameterBuilder();
        //更新参数名称
        tokenPar.name("token")
                //更新参数的说明
                .description("用户token")
                //更新参数的默认值
                .defaultValue("")
                //表示方便的方法来推断模型参考巩固或找出什么可以滚到另一个
                .modelRef(new ModelRef("string"))
                //更新参数的类型，可能是header, cookie, body, query等
                .parameterType("header")
                //如果参数是必需的或可选的，则更新
                .required(false)
                .build();

        pars.add(tokenPar.build());

        //指定使用Swagger2规范
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("服务监控项目-API文档")
                        //描述字段支持Markdown语法
                        .description("# 本文档描述了后台管理系统微服务接口定义")
                        .version("1.0")
                        .build())
                //分组名称
//                .groupName("adminApi")
                //select()返回ApiSelectorBuilder的一个实例，以提供对通过swagger公开的端点的细粒度控制
                .select()
                //api()允许使用谓词选择RequestHandler。这里的示例使用any谓词(默认)。提供的开箱即用谓词有any、none、withClassAnnotation、withMethodAnnotation和basePackage。这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.beyond.watchservice.controller"))
                //paths()允许使用谓词选择Path。这里的示例使用any谓词(默认)。我们开箱即用地为regex、ant、any、none提供谓词。
                .paths(PathSelectors.any())
                //选择器需要在配置api和路径选择器之后构建。
                .build()
                //允许全局配置默认路径-/请求-/头参数，这些默认路径-/请求-/头参数对于api的每个其他操作都是通用的，但在spring控制器方法签名中不需要(例如身份验证信息)。这里添加的参数将成为生成swagger规范中每个API操作的一部分。根据设置安全性的方式，所使用的头的名称可能需要不同。覆盖此值是覆盖默认行为的一种方法。
                .globalOperationParameters(pars);

        return docket;
    }




}
