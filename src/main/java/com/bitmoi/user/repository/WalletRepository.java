package com.bitmoi.user.repository;

import com.bitmoi.user.model.User;
import com.bitmoi.user.model.Wallet;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface WalletRepository extends ReactiveCrudRepository<Wallet, Long> {

    @Query("SELECT count(*) FROM USER WHERE id=:email")
    Mono<Long> findByEmail(String email);

    // @Query("INSERT INTO USER VALUES()")
    // Mono<User> join(User user);
}
