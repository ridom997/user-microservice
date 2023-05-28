package com.pragma.powerup.usermicroservice.configuration;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.pragma.powerup.usermicroservice.configuration.Constants.AUTHORIZATION_HEADER;

@Configuration
@AllArgsConstructor
public class FeignCloudConfiguration implements RequestInterceptor {

    private HttpServletRequest httpServletRequest;
    @Bean
    Logger.Level feignLoggerLevel(){
        return  Logger.Level.FULL;
    }

    public String getBearerTokenHeader(){
        return httpServletRequest.getHeader(AUTHORIZATION_HEADER);
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header(AUTHORIZATION_HEADER, getBearerTokenHeader());
    }
}
