package com.springboot.app.config;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.google.common.base.Predicate;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

	Parameter authHeader = new ParameterBuilder().parameterType("header").name("Authorization").defaultValue("Bearer ")
			.modelRef(new ModelRef("string")).build();
	
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.any()).paths(postPaths()).build()
				.globalOperationParameters(Collections.singletonList(authHeader));

	}

	private Predicate<String> postPaths() {
		return or(regex("/api/v1/users.*"), regex("/api/v1/tyres.*"), regex("/api/v1/login.*"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Spring Boot App").description("Spring Boot API details")
				.termsOfServiceUrl("").contact("abc@gamil.com").license("LICENSE of API").licenseUrl("API License URL")
				.version("1.0").build();
	}

	

}