package ru.quiz.generator.app.mapper;

import org.springframework.stereotype.Component;
import ru.quiz.generator.dto.*;
import ru.quiz.generator.dto.header.HeaderDTO;
import ru.quiz.generator.dto.model.CookieModelWithEnemyDTO;
import ru.quiz.generator.model.AuthModel;
import ru.quiz.generator.model.SessionModel;
import ru.quiz.generator.utils.CookieUtil;

import java.util.Date;

@Component
public class CookieGeneratorMapper {

    private final String FAILURE_REGISTRATION = "Ошибка регистранции.";
    private final String ALREADY_REGISTERED = "Пользователь с данным логином уже существует в системе.";
    private final String SUCCESS_REGISTRATION = "Регистрация завершена успешно.";

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
                return SUCCESS_REGISTRATION;
            case ALREADY_REGISTERED:
                return ALREADY_REGISTERED;
            case FAILURE:
                return FAILURE_REGISTRATION;
        }
        return FAILURE_REGISTRATION;

    }
}
