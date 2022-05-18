package com.bitmoi.user.service;

import javax.security.auth.login.LoginException;

import com.bitmoi.user.dto.UserJoinResponse;
import com.bitmoi.user.dto.WalletResponse;
import com.bitmoi.user.model.LoginJwt;
import com.bitmoi.user.model.User;
import com.bitmoi.user.model.UserSave;
import com.bitmoi.user.model.Wallet;
import com.bitmoi.user.model.WalletCoin;
import com.bitmoi.user.repository.CoinRepository;
import com.bitmoi.user.repository.UserRepository;
import com.bitmoi.user.repository.WalletRepository;
import com.bitmoi.user.util.JwtProvider;
import com.bitmoi.user.util.currentTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${initialFunds}")
    String initialFunds;

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
                            quantity = Float.parseFloat(initialFunds);
                        } else {
                            quantity = 0;
                        }
                        Wallet wallet = Wallet.builder()
                                .userId(user.getUserId())
                                .coinId(coin.getCoin_id())
                                .quantity(quantity)
                                .avgPrice(0)
                                .waitingQty(0.0f)
                                .build();
                        walletRepository.save(wallet).subscribe();
                    }).subscribe();
                })
                .flatMap(it -> {
                    return Mono.just(UserJoinResponse.builder().status(200).message("SUCCESS").build());
                }).onErrorReturn(UserJoinResponse.builder().status(403).message("FAIL").build());
    }

    @Override
    public Mono<UserJoinResponse> check(ServerRequest request) {
        return request.bodyToMono(UserSave.class)
                .flatMap(it -> userRepository.findByEmail(it.getEmail()))
                .flatMap(results -> {
                    if (results > 0) {
                        return Mono.just(UserJoinResponse.builder().status(403).message("FAIL").build());
                    }
                    return Mono.just(UserJoinResponse.builder().status(200).message("SUCCESS").build());
                });
    }

    @Override
    public Mono<LoginJwt> login(ServerRequest request) {
        return request.bodyToMono(UserSave.class).flatMap(user -> {
            return userRepository.findByIdAndPassword(user.getEmail(), user.getPassword());
        }).flatMap(it -> {
            String tokken = jwtProvider.createJwtToken(it);
            return Mono.just(LoginJwt.builder().status(200).accessToken(tokken).build());
        }).switchIfEmpty(Mono.just(LoginJwt.builder().status(403).accessToken("").build()));
    }

    @Override
    public Mono<WalletResponse> wallet(ServerRequest request) {
        return Mono.just(jwtProvider.decode(request.headers().asHttpHeaders().getFirst("Authorization")))
                .flatMap(walletRepository::findByUserId)
                .flatMap(wallets -> {
                    WalletResponse walletResponse = new WalletResponse();
                    float purchaseAmount = 0.0f;
                    float appraisalAmount = 0.0f;
                    float krw = 0.0f;
                    for (WalletCoin wallet : wallets) {
                        if (wallet.getCoinId() == 10) {
                            krw = wallet.getQuantity();
                            continue;
                        }
                        purchaseAmount += wallet.getQuantity() * wallet.getAvgPrice();
                        appraisalAmount += wallet.getPrice() * wallet.getQuantity();
                        System.out.println(purchaseAmount + " " + appraisalAmount);
                    }
                    walletResponse.setKrw(krw);
                    walletResponse.setHoldings(appraisalAmount + krw);
                    walletResponse.setValuationPL(appraisalAmount - purchaseAmount);
                    walletResponse.setYield((walletResponse.getHoldings() - Float.parseFloat(initialFunds)) * 100
                            / Float.parseFloat(initialFunds));
                    return Mono.just(walletResponse);
                });
    }

}
