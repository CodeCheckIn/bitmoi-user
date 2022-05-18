package com.bitmoi.user.repository;

import com.bitmoi.user.dto.WalletResponse;
import com.bitmoi.user.model.Wallet;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface WalletRepository extends ReactiveCrudRepository<Wallet, Long> {
    @Query("select "
            + "(select quantity from WALLET where user_id=W.user_id and coin_id=10) krw, "
            + "0 holdings, "
            + "sum(W.avg_price*W.quantity) purchaseAmount, "
            + "sum(W.quantity) appraisalAmount, "
            + "0 valuationPL, "
            + "0 yield "
            + "from WALLET W, "
            + "	 COIN C "
            + "where W.user_id = :ids "
            + "and W.coin_id <> 10 "
            + "and W.coin_id=C.coin_id")
    Mono<WalletResponse> findByUserId(int ids);

}
