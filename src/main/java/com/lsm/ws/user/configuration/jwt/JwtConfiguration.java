package com.lsm.ws.user.configuration.jwt;

import com.lsm.ws.user.infrastructure.jwt.JwtAccessAspect;
import com.lsm.ws.user.infrastructure.rest.context.RequestContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {

    @Bean
    JwtAccessAspect jwtAccessAspect(RequestContext requestContext){
        return new JwtAccessAspect(requestContext);
    }
}
