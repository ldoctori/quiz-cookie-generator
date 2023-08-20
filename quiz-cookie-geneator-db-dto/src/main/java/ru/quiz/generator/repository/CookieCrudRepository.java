package ru.quiz.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.quiz.generator.model.CookieModel;

import java.util.Optional;

@Repository
public interface CookieCrudRepository extends JpaRepository<CookieModel, Long> {

//        Optional<CookieModel> findByPlayer1IsEmptyOrPlayer2IsEmpty();
        Optional<CookieModel> findByPlayer1(String player1);



}
