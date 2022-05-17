package com.bitmoi.user.repository;

import com.bitmoi.user.model.User;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    @Query("SELECT count(*) FROM USER WHERE id=:email")
    Mono<Long> findByEmail(String email);

    Mono<User> findByIdAndPassword(String id, String password);
    // @Query("INSERT INTO USER VALUES()")
    // Mono<User> join(User user);
}
