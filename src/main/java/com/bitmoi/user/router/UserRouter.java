package com.bitmoi.user.router;

import com.bitmoi.user.handler.UserHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@EnableWebFlux
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> router(UserHandler userHandler) {
        return RouterFunctions.route()
                .POST("/user/register", userHandler::register)
                .POST("/user/join", userHandler::join)
                .GET("/user", userHandler::getAll)
                .POST("/user/check", userHandler::check)
                .POST("/user/login", userHandler::login)
                .build();
    }
}
