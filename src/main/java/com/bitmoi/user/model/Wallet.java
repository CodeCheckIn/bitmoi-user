package com.bitmoi.user.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table("WALLET")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
public class Wallet {
    @Id
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

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
