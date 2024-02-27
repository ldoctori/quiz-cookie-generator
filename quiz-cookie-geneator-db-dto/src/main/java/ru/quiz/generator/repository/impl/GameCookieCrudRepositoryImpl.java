package ru.quiz.generator.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.quiz.generator.enums.TableFieldsEnum;
import ru.quiz.generator.dto.rq.GetGameCookieRqDTO;
import ru.quiz.generator.dto.model.CookieModelWithEnemyDTO;
import ru.quiz.generator.exception.TableUpdateException;
import ru.quiz.generator.repository.GameCookieCrudRepository;
import ru.quiz.generator.utils.CookieUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GameCookieCrudRepositoryImpl implements GameCookieCrudRepository {

        private final JdbcTemplate jdbcTemplate;

        @Override
        public Optional<?> getCookie(GetGameCookieRqDTO getGameCookieRqDTO) throws DataAccessException, TableUpdateException {

                Optional<CookieModelWithEnemyDTO> optionalCookieModel = checkIsPlayerWithValidGameCookie(getGameCookieRqDTO.getTheme(),
                                                                                                getGameCookieRqDTO.getSessionCookie());
                if (optionalCookieModel.isPresent())
                        return optionalCookieModel;

                optionalCookieModel = findCookie(getGameCookieRqDTO.getTheme());
                if (optionalCookieModel.isPresent()) {
                        if (setPlayer2(getGameCookieRqDTO.getSessionCookie(), getGameCookieRqDTO.getTheme(), optionalCookieModel.get().getCookie())) {
                                return optionalCookieModel;
                        } else {
                                throw new TableUpdateException();
                        }
                } else {
                        String cookie = createCookieAndSetPlayer1(getGameCookieRqDTO.getSessionCookie(), getGameCookieRqDTO.getTheme());
                        if (cookie != null) {
                                return Optional.of(new CookieModelWithEnemyDTO()
                                        .withCookie(cookie)
                                        .withEnemyWaiting(true));
                        }
                }
                throw new TableUpdateException();
        }

        private Optional<CookieModelWithEnemyDTO> checkIsPlayerWithValidGameCookie(String theme, String sessionCookie) throws DataAccessException {
                List<CookieModelWithEnemyDTO> cookieModelWithEnemyDTOS = jdbcTemplate.query("SELECT * FROM GAME_COOKIE_SCHEMA."
                        + theme + " where " + TableFieldsEnum.SESSION_OF_PLAYER2.label + "='" + sessionCookie
                        + "' OR " + TableFieldsEnum.SESSION_OF_PLAYER1.label + "='" + sessionCookie + "'", (resultSet, rowNum) -> {
                        String sessionOfPlayer1 = resultSet.getString(TableFieldsEnum.SESSION_OF_PLAYER1.label);
                        String sessionOfPlayer2 = resultSet.getString(TableFieldsEnum.SESSION_OF_PLAYER2.label);

                        CookieModelWithEnemyDTO cookieModelWithEnemyDTO = new CookieModelWithEnemyDTO()
                                .withCookie(resultSet.getString(TableFieldsEnum.GAME_COOKIE.label))
                                .withCreationTime(resultSet.getDate(TableFieldsEnum.CREATION_TIME.label));
                        if (sessionCookie.equals(sessionOfPlayer1)) {
                                cookieModelWithEnemyDTO.withEnemyWaiting(sessionOfPlayer2 == null)
                                        .withEnemy(sessionOfPlayer2);
                        } else if (sessionCookie.equals(sessionOfPlayer2)) {
                                cookieModelWithEnemyDTO.withEnemyWaiting(sessionOfPlayer1 == null)
                                        .withEnemy(sessionOfPlayer1);
                        } else {
                                cookieModelWithEnemyDTO.withEnemyWaiting(true);
                        }
                        return cookieModelWithEnemyDTO;
                        }
                       );
                if (cookieModelWithEnemyDTOS.isEmpty()) {
                        return Optional.empty();
                }
                return Optional.of(cookieModelWithEnemyDTOS.get(0));
        }

        private Optional<CookieModelWithEnemyDTO> findCookie(String theme) throws DataAccessException {
                List<CookieModelWithEnemyDTO> cookieModelWithEnemyDTOS = jdbcTemplate.query("SELECT * FROM GAME_COOKIE_SCHEMA." + theme
                                        + " WHERE " + TableFieldsEnum.GAME_COOKIE.label + " IS NOT NULL AND "
                                        + TableFieldsEnum.SESSION_OF_PLAYER2.label + " IS NULL ",
                                (resultSet, rowNum) -> new CookieModelWithEnemyDTO()
                                        .withId(resultSet.getLong(TableFieldsEnum.ID.label))
                                        .withCookie(resultSet.getString(TableFieldsEnum.GAME_COOKIE.label))
                                        .withEnemy(resultSet.getString(TableFieldsEnum.SESSION_OF_PLAYER1.label))
                                        .withCreationTime(resultSet.getDate(TableFieldsEnum.CREATION_TIME.label))
                                        .withEnemyWaiting(false));
                return cookieModelWithEnemyDTOS.isEmpty() ? Optional.empty() : Optional.of(cookieModelWithEnemyDTOS.get(0));
        }

        private boolean setPlayer2(String playerName, String theme, String cookie) throws DataAccessException {

            return jdbcTemplate.update("update GAME_COOKIE_SCHEMA." + theme
                            + " set " + TableFieldsEnum.SESSION_OF_PLAYER2.label + " = ?"
                            + ", " + TableFieldsEnum.CREATION_TIME.label + " = ?"
                            + " where " + TableFieldsEnum.GAME_COOKIE.label + " = '" + cookie + "'",
                    playerName,
                    new Date()) == 1;
        }

        private String createCookieAndSetPlayer1(String playerName, String theme) throws DataAccessException {
                String gameCookie = CookieUtil.generateCookieString();

                if ( jdbcTemplate.update("INSERT INTO GAME_COOKIE_SCHEMA." + theme
                        + "(" + TableFieldsEnum.GAME_COOKIE.label + ","
                                + TableFieldsEnum.CREATION_TIME.label + ","
                                + TableFieldsEnum.SESSION_OF_PLAYER1.label + ")  values (?, ?, ?)",
                        gameCookie,
                        new Date(),
                        playerName) == 1) {
                        return gameCookie;
                } else
                        return null;
        }
}
