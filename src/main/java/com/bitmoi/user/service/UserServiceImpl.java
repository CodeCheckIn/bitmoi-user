package com.bitmoi.user.service;

import java.time.LocalDateTime;

import javax.security.auth.login.LoginException;

import com.bitmoi.user.dto.UserJoinResponse;
import com.bitmoi.user.dto.WalletRequest;
import com.bitmoi.user.model.Coin;
import com.bitmoi.user.model.LoginJwt;
import com.bitmoi.user.model.User;
import com.bitmoi.user.model.UserSave;
import com.bitmoi.user.model.UserType;
import com.bitmoi.user.model.Wallet;
import com.bitmoi.user.repository.CoinRepository;
import com.bitmoi.user.repository.UserRepository;
import com.bitmoi.user.repository.WalletRepository;
import com.bitmoi.user.util.JwtProvider;
import com.bitmoi.user.util.currentTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final CoinRepository coinRepository;
    private final currentTime current;
    private final JwtProvider jwtProvider;

    // 학생 등록
    @Override
    public Mono<UserJoinResponse> join(ServerRequest request) {
        return request.bodyToMono(UserSave.class) // 값 불러오기
                .map(it -> { // user로 세팅
                    return User.builder()
                            .id(it.getEmail())
                            .name(it.getName())
                            .password(it.getPassword())
                            .phone(it.getPhone())
                            .build();
                })
                .flatMap(userRepository::save) // 저장
                .doOnNext(user -> // 지갑 생성
                {
                    System.out.println("user => " + user);
                    coinRepository.findAll().doOnNext(coin -> {
                        float quantity = 0.0f;
                        if (coin.getName().equals("KRW")) {
                            quantity = 100000;
                        } else {
                            quantity = 0;
                        }
                        System.out.println("coin => " + coin);
                        Wallet wallet = Wallet.builder()
                                .userId(user.getUserId())
                                .coinId(coin.getCoin_id())
                                .quantity(quantity)
                                .avgPrice(0)
                                .build();
                        System.out.println("coin : " + wallet);
                        walletRepository.save(wallet).subscribe();
                        // System.out.println("coin : " + wallet);
                    }).subscribe();
                }).flatMap(it -> {
                    return Mono.just(UserJoinResponse.builder().message("SUCCESS").build());
                }).log();
    }

    @Override
    public Mono<UserJoinResponse> check(ServerRequest request) {
        return request.bodyToMono(UserSave.class)
                .flatMap(it -> userRepository.findByEmail(it.getEmail()))
                .flatMap(results -> {
                    System.out.println("result: " + results);
                    if (results > 0) {
                        return Mono.just(UserJoinResponse.builder().message("FAIL").build());
                    }
                    return Mono.just(UserJoinResponse.builder().message("SUCCESS").build());
                });
    }

    @Override
    public Mono<String> test(String request) {
        System.out.println(request);
        return null;
    }

    @Override
    public Mono<LoginJwt> login(ServerRequest request) {
        return request.bodyToMono(UserSave.class).flatMap(user -> {
            return userRepository.findByIdAndPassword(user.getEmail(), user.getPassword());
        }).flatMap(it -> {
            String tokken = jwtProvider.createJwtToken(it, 100000);
            return Mono.just(LoginJwt.builder().accessToken(tokken).build());
        }).switchIfEmpty(Mono.just(LoginJwt.builder().accessToken("존재하지 않은 회원입니다.").build()));
    }

    @Override
    public Flux<Wallet> wallet(ServerRequest request) {
        return request.bodyToFlux(WalletRequest.class).flatMap(user -> {
            return walletRepository.findByUserId(user.getEmail());
        });
    }

}
