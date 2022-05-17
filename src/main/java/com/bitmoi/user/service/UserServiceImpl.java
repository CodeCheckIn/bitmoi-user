package com.bitmoi.user.service;

import javax.security.auth.login.LoginException;

import com.bitmoi.user.dto.UserJoinResponse;
import com.bitmoi.user.model.LoginJwt;
import com.bitmoi.user.model.User;
import com.bitmoi.user.model.UserSave;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final CoinRepository coinRepository;
    private final JwtProvider jwtProvider;

    // 학생 등록
    @Override
    public Mono<UserJoinResponse> join(ServerRequest request) {
        return request.bodyToMono(UserSave.class) // 값 불러오기
                .flatMap(userInfo -> { // user로 세팅
                    return userRepository.findByEmail(userInfo.getEmail())
                            .flatMap(
                                    results -> {
                                        System.out.println("results: " + results);
                                        if (results > 0) {
                                            return Mono.error(new LoginException("이미 존재하는 메일주소입니다."));
                                        }
                                        return userRepository.save(User.builder()
                                                .id(userInfo.getEmail())
                                                .name(userInfo.getName())
                                                .password(userInfo.getPassword())
                                                .phone(userInfo.getPhone())
                                                .build());
                                    });
                })
                .doOnNext(user -> // 지갑 생성
                {
                    coinRepository.findAll().doOnNext(coin -> {
                        float quantity = 0.0f;
                        if (coin.getName().equals("KRW")) {
                            quantity = 100000;
                        } else {
                            quantity = 0;
                        }
                        Wallet wallet = Wallet.builder()
                                .userId(user.getUserId())
                                .coinId(coin.getCoin_id())
                                .quantity(quantity)
                                .avgPrice(0)
                                .build();
                        System.out.println("coin : " + wallet);
                        walletRepository.save(wallet).subscribe();
                    }).subscribe();
                })
                .flatMap(it -> {
                    return Mono.just(UserJoinResponse.builder().message("SUCCESS").build());
                }).onErrorResume(error -> Mono.just(UserJoinResponse.builder().message(error.getMessage()).build()));
    }

    @Override
    public Mono<UserJoinResponse> check(ServerRequest request) {
        return request.bodyToMono(UserSave.class)
                .flatMap(it -> userRepository.findByEmail(it.getEmail()))
                .flatMap(results -> {
                    if (results > 0) {
                        return Mono.just(UserJoinResponse.builder().message("FAIL").build());
                    }
                    return Mono.just(UserJoinResponse.builder().message("SUCCESS").build());
                });
    }

    @Override
    public Mono<LoginJwt> login(ServerRequest request) {
        return request.bodyToMono(UserSave.class).flatMap(user -> {
            return userRepository.findByIdAndPassword(user.getEmail(), user.getPassword());
        }).flatMap(it -> {
            String tokken = jwtProvider.createJwtToken(it);
            return Mono.just(LoginJwt.builder().accessToken(tokken).build());
        }).switchIfEmpty(Mono.just(LoginJwt.builder().accessToken("존재하지 않은 회원입니다.").build()));
    }

    @Override
    public Flux<Wallet> wallet(ServerRequest request) {
        return Flux.just(jwtProvider.decode(request.headers().asHttpHeaders().getFirst("Authorization")))
                .flatMap(user -> {
                    return walletRepository.findByUserId(user);
                });
    }

}
