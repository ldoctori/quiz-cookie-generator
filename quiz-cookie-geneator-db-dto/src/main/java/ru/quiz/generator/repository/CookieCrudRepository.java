package ru.quiz.generator.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.quiz.generator.TableFieldsEnum;
import ru.quiz.generator.dto.GetCookieRqDTO;
import ru.quiz.generator.dto.model.CookieModelWithEnemyDTO;
import ru.quiz.generator.exception.TableUpdateException;
import ru.quiz.generator.utils.CookieUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CookieCrudRepository {

        private final JdbcTemplate jdbcTemplate;

        public Optional<?> getCookie(GetCookieRqDTO getCookieRqDTO) throws DataAccessException {

                Optional<List<CookieModelWithEnemyDTO>> optionalList = findCookie(getCookieRqDTO.getTheme());
                if (optionalList.isPresent() && !optionalList.get().isEmpty()) {
                        CookieModelWithEnemyDTO cookieModel = optionalList.get().get(0);
                        if (setPlayer2(getCookieRqDTO.getPlayerName(),
                                getCookieRqDTO.getTheme(),
                                cookieModel.getCookie()) == 1) {
                                return Optional.of(cookieModel);
                        } else {
                                throw new TableUpdateException();
                        }
                } else {
                        String cookie = createCookieAndSetPlayer1(getCookieRqDTO.getPlayerName(), getCookieRqDTO.getTheme());
                        if (cookie != null) {
                                return Optional.of(new CookieModelWithEnemyDTO()
                                        .withCookie(cookie)
                                        .withEnemyWaiting(true));
                        }
                }
                throw new TableUpdateException();
        }

        public Optional<List<CookieModelWithEnemyDTO>> findCookie(String theme) throws DataAccessException {
                return Optional.of(jdbcTemplate.query("SELECT * FROM COOKIE_SCHEMA." + theme
                                        + " WHERE " + TableFieldsEnum.COOKIE.label + " IS NOT NULL AND "
                                        + TableFieldsEnum.PLAYER2.label + " IS NULL ",
                                (resultSet, rowNum) -> new CookieModelWithEnemyDTO()
                                        .withId(resultSet.getLong(TableFieldsEnum.ID.label))
                                        .withCookie(resultSet.getString(TableFieldsEnum.COOKIE.label))
                                        .withEnemy(resultSet.getString(TableFieldsEnum.PLAYER1.label))
                                        .withCreationTime(resultSet.getDate(TableFieldsEnum.CREATION_TIME.label))
                                        .withEnemyWaiting(false)));

        }

        public int setPlayer2(String playerName, String theme, String cookie) throws DataAccessException {
                return jdbcTemplate.update("update COOKIE_SCHEMA." + theme
                        + " set " + TableFieldsEnum.PLAYER2.label + " = ?"
                        + ", " + TableFieldsEnum.CREATION_TIME.label + " = ?"
                        + " where " + TableFieldsEnum.COOKIE.label + " = '" + cookie + "'",
                        playerName,
                        new Date());
        }

        public String createCookieAndSetPlayer1(String playerName, String theme) throws DataAccessException {
                String cookie = CookieUtil.generateCookieString();

                if ( jdbcTemplate.update("INSERT INTO COOKIE_SCHEMA." + theme
                        + "(cookie, creationTime, player1)  values (?, ?, ?)",
                        cookie,
                        new Date(),
                        playerName) == 1) {
                        return cookie;
                } else
                        return null;

        }
}
