package com.bitmoi.user.model;

import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor

@ToString
@Builder
@Getter
@Setter
public class WalletCoin {
    @Column
    private int walletId;
    @Column
    private int userId;
    @Column
    private int coinId;
    @Column
    private float quantity;
    @Column
    private float avgPrice;
    @Column
    private float waitingQty;
    @Column
    private float price;
}
