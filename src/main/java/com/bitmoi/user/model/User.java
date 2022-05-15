package com.bitmoi.user.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Table("USER")
public class User {

    @Id
    private int userId;

    @Column
    private String id;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String phone;

    @Column
    private String createdAt;

    @Column
    private String updatedAt;

}
