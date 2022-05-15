package com.bitmoi.user.model;

import java.time.LocalDateTime;
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

@Table("COIN")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
public class Coin {
    @Id
    private int coin_id;
    @Column
    private String name;
    @Column
    private int price;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
