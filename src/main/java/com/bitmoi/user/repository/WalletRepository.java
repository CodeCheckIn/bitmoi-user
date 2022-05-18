package com.bitmoi.user.repository;

import java.util.ArrayList;
import com.bitmoi.user.model.Wallet;
import com.bitmoi.user.model.WalletCoin;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface WalletRepository extends ReactiveCrudRepository<Wallet, Long> {
    // @Query("SELECT "
    // + "W.avg_price*W.quantity purchaseAmount, "
    // + "W.quantity*C.price appraisalAmount "
    // + "FROM WALLET W, "
    // + " COIN C "
    // + "WHERE W.user_id = :ids "
    // + "and W.coin_id <> 10 "
    // + "and W.coin_id=C.coin_id")
    @Query("SELECT W.* FROM WALLET W WHERE W.user_id=:ids")
    Flux<Wallet> findByUserId(int ids);

}
