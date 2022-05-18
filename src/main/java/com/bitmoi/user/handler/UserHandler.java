package com.bitmoi.user.handler;

import com.bitmoi.user.Exception.LoginException;
import com.bitmoi.user.dto.UserJoinResponse;
import com.bitmoi.user.model.LoginJwt;
import com.bitmoi.user.model.User;
import com.bitmoi.user.model.Wallet;
import com.bitmoi.user.repository.WalletRepository;
import com.bitmoi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService userService;

    // public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
    // return ok().body(userService.findAll(), User.class).log();
    // }

    public Mono<ServerResponse> join(ServerRequest serverRequest) {
        Mono<UserJoinResponse> response = userService.join(serverRequest).subscribeOn(Schedulers.boundedElastic());
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, UserJoinResponse.class)
                .onErrorResume(error -> {
                    if (error instanceof LoginException) {
                        return ServerResponse.status(403).build();
                    }
                    return ServerResponse.badRequest().build();
                }).log();
    }

    public Mono<ServerResponse> check(ServerRequest serverRequest) {
        Mono<UserJoinResponse> response = userService.check(serverRequest).subscribeOn(Schedulers.boundedElastic());
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, UserJoinResponse.class)
                .onErrorResume(error -> {
                    if (error instanceof LoginException) {
                        return ServerResponse.status(403).build();
                    }
                    return ServerResponse.badRequest().build();
                }).log();
    }

    public Mono<ServerResponse> login(ServerRequest serverRequest) {
        Mono<LoginJwt> response = userService.login(serverRequest).subscribeOn(Schedulers.boundedElastic());
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, LoginJwt.class)
                .onErrorResume(error -> {
                    System.out.println(error.toString());
                    if (error instanceof LoginException) {
                        return ServerResponse.status(403).build();
                    }
                    return ServerResponse.badRequest().build();
                }).log();
    }

    public Mono<ServerResponse> wallet(ServerRequest serverRequest) {
        Flux<Wallet> response = userService.wallet(serverRequest).subscribeOn(Schedulers.boundedElastic());
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, Wallet.class)
                .onErrorResume(error -> {

                    if (error instanceof LoginException) {
                        return ServerResponse.status(403).build();
                    }
                    return ServerResponse.badRequest().build();
                }).log();
    }
}
