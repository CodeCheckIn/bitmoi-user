package com.bitmoi.user.repository;

import com.bitmoi.user.dto.WalletResponse;
import com.bitmoi.user.model.Wallet;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface WalletRepository extends ReactiveCrudRepository<Wallet, Long> {
    @Query("SELECT "
            + "SUM(W.avg_price*W.quantity) purchaseAmount, "
            + "SUM(W.quantity*C.price) appraisalAmount, "
            + "FROM WALLET W, "
            + "	 COIN C "
            + "WHERE W.user_id = :ids "
            + "and W.coin_id <> 10 "
            + "and W.coin_id=C.coin_id")
    Mono<WalletResponse> findByUserId(int ids);

}
