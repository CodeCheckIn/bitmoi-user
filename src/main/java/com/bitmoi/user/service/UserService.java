package com.bitmoi.user.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

import com.bitmoi.user.dto.UserJoinResponse;
import com.bitmoi.user.dto.UserRegistrationResponse;
import com.bitmoi.user.model.LoginJwt;
import com.bitmoi.user.model.User;
import com.bitmoi.user.model.UserType;

public interface UserService {

    // 학생 등록
    Mono<UserJoinResponse> join(ServerRequest request);

    Mono<String> test(String request);

    Mono<UserJoinResponse> check(ServerRequest request);

    Mono<LoginJwt> login(ServerRequest request);
    // Mono<User> registrationStudent(ServerRequest request);

    // UserRegistrationResponse registrationTeacher(ServerRequest serverRequest);
    //
    // UserRegistrationResponse registrationStudent(ServerRequest serverRequest);
    //
    // UserLoginResponse loginService(ServerRequest serverRequest);

}
