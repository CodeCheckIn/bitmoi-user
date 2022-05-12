package com.bitmoi.user.handler;

import com.bitmoi.user.dto.UserJoinResponse;
import com.bitmoi.user.dto.UserRegistrationResponse;
import com.bitmoi.user.model.Coin;
import com.bitmoi.user.model.LoginJwt;
import com.bitmoi.user.model.User;
import com.bitmoi.user.model.UserSave;
import com.bitmoi.user.model.UserType;
import com.bitmoi.user.model.Wallet;
import com.bitmoi.user.repository.CoinRepository;
import com.bitmoi.user.repository.UserRepository;
import com.bitmoi.user.repository.WalletRepository;
import com.bitmoi.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.h2.util.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.security.Principal;
import java.time.Duration;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserHandler {

    private final Validator validator;
    private final UserService userService;
    private final WalletRepository coinRepository;
    private final ObjectMapper objectMapper;

    public Mono<ServerResponse> register(ServerRequest request) {
        Mono<ServerResponse> result = null;
        // return ok()
        // .contentType(MediaType.APPLICATION_JSON)
        // .bodyValue("Test");
        return result.flatMap(data -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data))
                .onErrorResume(error -> ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        return ok().body(coinRepository.findAll(), Wallet.class).log();
    }

    public Mono<ServerResponse> join(ServerRequest serverRequest) {
        Mono<UserJoinResponse> response = userService.join(serverRequest).subscribeOn(Schedulers.boundedElastic());
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, User.class)
                .onErrorResume(error -> ServerResponse.badRequest().build()).log();
    }

    public Mono<ServerResponse> check(ServerRequest serverRequest) {
        Mono<UserJoinResponse> response = userService.check(serverRequest).subscribeOn(Schedulers.boundedElastic());
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, UserJoinResponse.class)
                .onErrorResume(error -> ServerResponse.badRequest().build()).log();
    }

    public Mono<ServerResponse> login(ServerRequest serverRequest) {
        Mono<LoginJwt> response = userService.login(serverRequest).subscribeOn(Schedulers.boundedElastic());
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, LoginJwt.class)
                .onErrorResume(error -> ServerResponse.badRequest().build()).log();
    }
}
