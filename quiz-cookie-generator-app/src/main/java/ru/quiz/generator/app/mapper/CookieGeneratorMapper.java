package ru.quiz.generator.app.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.quiz.generator.dto.GetCookieRqDTO;
import ru.quiz.generator.dto.GetCookieRsDTO;
import ru.quiz.generator.dto.header.HeaderDTO;
import ru.quiz.generator.dto.model.CookieModelWithEnemyDTO;

@Component
public class CookieGeneratorMapper {

    public GetCookieRsDTO generateCookieRsDTO(CookieModelWithEnemyDTO cookieModel, GetCookieRqDTO getCookieRqDTO) {

        return new GetCookieRsDTO()
                .withHeader(getCookieRqDTO.getHeader().withStatus(HeaderDTO.Status.SUCCESS))
                .withGameCookie(cookieModel.getCookie())
                .withTheme(getCookieRqDTO.getTheme())
                .withEnemyName(cookieModel.getEnemy())
                .withEnemyWaiting(cookieModel.getEnemyWaiting());
    }

}
