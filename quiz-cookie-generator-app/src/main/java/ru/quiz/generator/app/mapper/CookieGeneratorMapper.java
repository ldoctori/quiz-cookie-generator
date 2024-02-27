package ru.quiz.generator.app.mapper;

import org.springframework.stereotype.Component;
import ru.quiz.generator.dto.header.HeaderDTO;
import ru.quiz.generator.dto.model.CookieModelWithEnemyDTO;
import ru.quiz.generator.dto.rq.AuthorizationRqDTO;
import ru.quiz.generator.dto.rq.GetGameCookieRqDTO;
import ru.quiz.generator.dto.rq.RegistrationRqDTO;
import ru.quiz.generator.dto.rs.AuthorizationRsDTO;
import ru.quiz.generator.dto.rs.GetGameCookieRsDTO;
import ru.quiz.generator.dto.rs.RegistrationRsDTO;
import ru.quiz.generator.model.AuthModel;
import ru.quiz.generator.model.SessionModel;
import ru.quiz.generator.utils.CookieUtil;

import java.util.Date;

import static ru.quiz.generator.app.enums.ResultDescriptionEnum.*;

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

    public SessionModel generateSessionModelEntity(AuthorizationRqDTO authorizationRqDTO){

        SessionModel sessionModel = new SessionModel();
        sessionModel.setSessionCookie(CookieUtil.generateCookieString());
        sessionModel.setCreationTime(new Date());
        sessionModel.setLogin(authorizationRqDTO.getLogin());

        return sessionModel;
    }

    public AuthorizationRsDTO generateGetSessionCookieRsDTO(SessionModel sessionModel, AuthorizationRqDTO authorizationRqDTO) {
        return new AuthorizationRsDTO().withHeader(authorizationRqDTO.getHeader().withStatus(HeaderDTO.Status.SUCCESS))
                .withSessionCookie(sessionModel.getSessionCookie());
    }

    public AuthModel generateAuthModel(RegistrationRqDTO registrationRqDTO) {

        AuthModel authModel = new AuthModel();
        authModel.setLogin(registrationRqDTO.getLogin());
        authModel.setPassword(registrationRqDTO.getPassword());
        authModel.setRegistrationDate(new Date());
        return authModel;

    }

    public RegistrationRsDTO generateRegistrationRsDTO(RegistrationRqDTO registrationRqDTO, HeaderDTO.Status rqStatus,
                                                       RegistrationRsDTO.RegistrationStatus registrationStatus) {
        assert registrationRqDTO.getHeader() != null;
        return new RegistrationRsDTO().withHeader(registrationRqDTO.getHeader().withStatus(rqStatus))
                .withRegistrationStatus(registrationStatus)
                .withRegistrationStatusDesc(getRegistrationStatusDesc(registrationStatus));
    }

    private String getRegistrationStatusDesc(RegistrationRsDTO.RegistrationStatus registrationStatus) {

        switch (registrationStatus) {
            case SUCCESS:
                return SUCCESS_REGISTRATION.label;
            case ALREADY_REGISTERED:
                return ALREADY_REGISTERED.label;
            case FAILURE:
                return FAILURE_REGISTRATION.label;
        }
        return FAILURE_REGISTRATION.label;

    }
}
