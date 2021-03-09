package com.ttn.restfulwebservices.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {

        final Contact DEFAULT_CONTACT = new Contact("athisii", "www.tothenew.com", "daiho.ekhe@tothenew.com");
        final ApiInfo DEFAULT_API_INFO = new ApiInfo(
                "User API",
                "Sample API for User",
                "1.0",
                "http://tothenew.com",
                DEFAULT_CONTACT,
                "Apache 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                Collections.emptyList()
        );
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO);
    }

}
