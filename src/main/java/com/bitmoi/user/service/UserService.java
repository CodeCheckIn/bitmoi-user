package com.bitmoi.user.service;

import org.springframework.web.reactive.function.server.ServerRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.bitmoi.user.dto.UserJoinResponse;
import com.bitmoi.user.dto.WalletResponse;
import com.bitmoi.user.model.LoginJwt;
import com.bitmoi.user.model.Wallet;

public interface UserService {

    Mono<UserJoinResponse> join(ServerRequest request);

    Mono<UserJoinResponse> check(ServerRequest request);

    Mono<LoginJwt> login(ServerRequest request);

    Mono<WalletResponse> wallet(ServerRequest request);
}
