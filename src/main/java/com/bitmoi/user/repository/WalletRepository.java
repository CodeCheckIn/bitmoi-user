package com.bitmoi.user.repository;

import com.bitmoi.user.model.User;
import com.bitmoi.user.model.Wallet;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface WalletRepository extends ReactiveCrudRepository<Wallet, Long> {

    @Query("SELECT * FROM WALLET, USER WHERE WALLET.user_id=USER.user_id and USER.id=:email")
    Flux<Wallet> findByUserId(String email);

    // @Query("INSERT INTO USER VALUES()")
    // Mono<User> join(User user);
}
