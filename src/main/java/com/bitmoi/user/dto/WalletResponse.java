package com.bitmoi.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WalletResponse {
    // 보유금액
    private float krw;
    // 보유자산
    private float holdings;
    // 매수금액
    private float purchaseAmount;
    // 평가금액
    private float appraisalAmount;
    // 평가손익
    private float valuationPL;
    // 수익률
    private float yield;
}
