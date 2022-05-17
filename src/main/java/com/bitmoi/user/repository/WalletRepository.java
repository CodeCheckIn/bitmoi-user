package com.bitmoi.user.repository;

import com.bitmoi.user.model.Wallet;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface WalletRepository extends ReactiveCrudRepository<Wallet, Long> {

    Flux<Wallet> findByUserId(int id);

}
