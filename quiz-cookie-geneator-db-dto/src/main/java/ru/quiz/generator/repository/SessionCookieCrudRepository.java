package ru.quiz.generator.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SessionCookieCrudRepository {

    private final JdbcTemplate jdbcTemplate;

}
