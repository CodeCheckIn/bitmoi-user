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
            + "(SELECT quantity FROM WALLET WHERE user_id=W.user_id and coin_id=10) krw, "
            + "0 holdings, "
            + "SUM(W.avg_price*W.quantity) purchase_amount, "
            + "SUM(W.quantity*C.price) appraisal_amount, "
            + "0 valuation_Pl, "
            + "0 yield "
            + "FROM WALLET W, "
            + "	 COIN C "
            + "WHERE W.user_id = :ids "
            + "and W.coin_id <> 10 "
            + "and W.coin_id=C.coin_id")
    Mono<WalletResponse> findByUserId(int ids);

}
