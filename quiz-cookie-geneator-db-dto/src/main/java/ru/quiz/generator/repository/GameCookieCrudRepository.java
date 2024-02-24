package ru.quiz.generator.repository;

import org.springframework.stereotype.Repository;
import ru.quiz.generator.dto.GetGameCookieRqDTO;
import java.util.Optional;

@Repository
public interface GameCookieCrudRepository {

    Optional<?> getCookie(GetGameCookieRqDTO getGameCookieRqDTO);

}
