package com.lsm.ws.user.configuration.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String DEMO_USER_GROUP = "demo-user";
    private static final String DEMO_USER_PATH = "/v1/api/user/**";

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                             .group(DEMO_USER_GROUP)
                             .pathsToMatch(DEMO_USER_PATH)
                             .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().description("Demo user microservice")
                                            .title("User microservice"))
                            .addServersItem(new Server().url("/"));
    }
}
