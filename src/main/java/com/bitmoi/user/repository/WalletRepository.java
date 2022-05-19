package com.bitmoi.user.repository;

import com.bitmoi.user.dto.RankingResponse;
import com.bitmoi.user.dto.WalletResponse;
import com.bitmoi.user.model.Wallet;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface WalletRepository extends ReactiveCrudRepository<Wallet, Long> {
    @Query("SELECT "
            + "(SELECT quantity FROM WALLET WHERE user_id=W.user_id and coin_id=9999) krw, "
            + "0 holdings, "
            + "SUM(W.avg_price*W.quantity) purchase_amount, "
            + "SUM(W.quantity*C.price) appraisal_amount, "
            + "0 valuation_Pl, "
            + "0 yield "
            + "FROM WALLET W, "
            + "	 COIN C "
            + "WHERE W.user_id = :ids "
            + "and W.coin_id <> 9999 "
            + "and W.coin_id=C.coin_id")
    Mono<WalletResponse> findByUserId(int ids);

    @Query("with rankings as( "
            + "select SUM(W.quantity*C.price) assets,U.user_id,U.name "
            + "from WALLET W, COIN C,USER U "
            + "where U.user_id=W.user_id and W.coin_id=C.coin_id group by W.user_id) "
            + "select rank() over(order by r.assets desc) as ranking,r.*,(r.assets-100000000)/1000000 yeild from rankings r")
    Flux<RankingResponse> findRanking();

    @Query("SELECT * FROM WALLET WHERE user_id=:id")
    Flux<Wallet> findWallet(int id);

}
