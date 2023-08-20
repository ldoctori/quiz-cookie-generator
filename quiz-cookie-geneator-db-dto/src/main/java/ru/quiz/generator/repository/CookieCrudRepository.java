package ru.quiz.generator.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.quiz.generator.model.CookieModel;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CookieCrudRepository {

        private final JdbcTemplate jdbcTemplate;

        public Optional<CookieModel> findByPlayer1(String playerName, String theme) {

                Optional.of(jdbcTemplate.query("SELECT * FROM COOKIE_SCHEMA.CHEMISTRY WHERE player1='Petuh'", ))

                return null;
        }




}
