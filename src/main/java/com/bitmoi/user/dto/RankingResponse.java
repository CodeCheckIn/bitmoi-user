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
public class RankingResponse {
    // 순위
    private int ranking;
    // 보유자산
    private float assets;
    // 유저아이디
    private int userId;
    // 이름
    private String name;
    // 수익률
    private float yeild;

}
