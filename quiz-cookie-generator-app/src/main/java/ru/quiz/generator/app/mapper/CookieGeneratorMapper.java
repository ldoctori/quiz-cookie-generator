package ru.quiz.generator.app.mapper;

import org.springframework.stereotype.Component;
import ru.quiz.generator.dto.GetGameCookieRqDTO;
import ru.quiz.generator.dto.GetGameCookieRsDTO;
import ru.quiz.generator.dto.GetSessionCookieRqDTO;
import ru.quiz.generator.dto.GetSessionCookieRsDTO;
import ru.quiz.generator.dto.header.HeaderDTO;
import ru.quiz.generator.dto.model.CookieModelWithEnemyDTO;
import ru.quiz.generator.model.SessionModel;
import ru.quiz.generator.utils.CookieUtil;

import java.util.Date;

@Component
public class CookieGeneratorMapper {

    public GetGameCookieRsDTO generateCookieRsDTO(CookieModelWithEnemyDTO cookieModel, GetGameCookieRqDTO getGameCookieRqDTO) {

        assert getGameCookieRqDTO.getHeader() != null;
        return new GetGameCookieRsDTO()
                .withHeader(getGameCookieRqDTO.getHeader().withStatus(HeaderDTO.Status.SUCCESS))
                .withGameCookie(cookieModel.getCookie())
                .withTheme(getGameCookieRqDTO.getTheme())
                .withEnemyName(cookieModel.getEnemy())
                .withEnemyWaiting(cookieModel.getEnemyWaiting());
    }

    public SessionModel generateSessionModelEntity(GetSessionCookieRqDTO getSessionCookieRqDTO){

        SessionModel sessionModel = new SessionModel();
        sessionModel.setSessionCookie(CookieUtil.generateCookieString());
        sessionModel.setCreationTime(new Date());
        sessionModel.setLogin(getSessionCookieRqDTO.getLogin());

        return sessionModel;
    }

    public GetSessionCookieRsDTO generateGetSessionCookieRsDTO(SessionModel sessionModel, GetSessionCookieRqDTO getSessionCookieRqDTO) {
        assert getSessionCookieRqDTO.getHeader() != null;
        return new GetSessionCookieRsDTO().withHeader(getSessionCookieRqDTO.getHeader().withStatus(HeaderDTO.Status.SUCCESS))
                .withSessionCookie(sessionModel.getSessionCookie());
    }
}
