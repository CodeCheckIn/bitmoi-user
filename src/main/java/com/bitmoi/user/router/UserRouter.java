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
                .GET("/user/wallet", userHandler::wallet)
                .POST("/user/join", userHandler::join)
                .POST("/user/check", userHandler::check)
                .POST("/user/login", userHandler::login)
                .GET("/user/ranking", userHandler::ranking)
                .GET("/user/asset/{userId}", userHandler::asset)
                .build();
    }
}
