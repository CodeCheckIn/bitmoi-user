package com.bitmoi.user.model;

import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
public class UserSave {
    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String phone;
}
