package ru.quiz.generator.app.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.quiz.generator.app.enums.ResultDescriptionEnum;
import ru.quiz.generator.app.mapper.CookieGeneratorMapper;
import ru.quiz.generator.dto.header.HeaderDTO;
import ru.quiz.generator.dto.model.CookieModelWithEnemyDTO;
import ru.quiz.generator.dto.rq.AuthorizationRqDTO;
import ru.quiz.generator.dto.rq.GetGameCookieRqDTO;
import ru.quiz.generator.dto.rq.RegistrationRqDTO;
import ru.quiz.generator.dto.rs.AuthorizationRsDTO;
import ru.quiz.generator.dto.rs.GetGameCookieRsDTO;
import ru.quiz.generator.dto.rs.RegistrationRsDTO;
import ru.quiz.generator.exception.TableUpdateException;
import ru.quiz.generator.model.AuthModel;
import ru.quiz.generator.model.SessionModel;
import ru.quiz.generator.repository.AuthCrudRepository;
import ru.quiz.generator.repository.GameCookieCrudRepository;
import ru.quiz.generator.repository.SessionCookieCrudRepository;
import ru.quiz.generator.utils.JsonUtil;

import javax.persistence.PersistenceException;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class GetCookieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetCookieService.class);

    private final GameCookieCrudRepository gameCookieCrudRepository;
    private final SessionCookieCrudRepository sessionCookieCrudRepository;
    private final AuthCrudRepository authCrudRepository;
    private final CookieGeneratorMapper cookieGeneratorMapper;

    public ResponseEntity<?> getGameCookieRsDTO(GetGameCookieRqDTO getGameCookieRqDTO) {

        assert getGameCookieRqDTO.getHeader() != null;
        try {
            Optional<?> optionalCookieModel = gameCookieCrudRepository.getCookie(getGameCookieRqDTO);
            LOGGER.info(JsonUtil.getPrettyJson(optionalCookieModel.get()));
            CookieModelWithEnemyDTO cookieModel = (CookieModelWithEnemyDTO) optionalCookieModel.get();
            GetGameCookieRsDTO getGameCookieRsDTO = cookieGeneratorMapper.generateCookieRsDTO(cookieModel, getGameCookieRqDTO);
            return ResponseEntity.ok(getGameCookieRsDTO);
        } catch (DataAccessException | TableUpdateException e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> authorizeAndGetSessionCookie(AuthorizationRqDTO authorizationRqDTO) {

        assert authorizationRqDTO.getHeader() != null;
        try {
            if (!authorize(authorizationRqDTO)) {
                return ResponseEntity.ok(new AuthorizationRsDTO().withHeader(authorizationRqDTO.getHeader().withStatus(HeaderDTO.Status.FAILURE))
                        .withErrorMessage(ResultDescriptionEnum.WRONG_LOGIN_OR_PASSOWRD.label));
            }
            AuthorizationRsDTO authorizationRsDTO = getSessionCookie(authorizationRqDTO);
            return ResponseEntity.ok(authorizationRsDTO);
        } catch (PersistenceException e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.ok(new AuthorizationRsDTO().withHeader(authorizationRqDTO.getHeader().withStatus(HeaderDTO.Status.FAILURE))
                    .withErrorMessage(ResultDescriptionEnum.AUTHORIZATION_ERROR.label));
        }
    }

    public ResponseEntity<?> registration(RegistrationRqDTO registrationRqDTO) {

        assert registrationRqDTO.getHeader() != null;
        AuthModel authModel = cookieGeneratorMapper.generateAuthModel(registrationRqDTO);
        AuthModel registeredModel = authCrudRepository.findByLogin(authModel.getLogin());
        if (registeredModel != null) {
            return ResponseEntity.ok(cookieGeneratorMapper.generateRegistrationRsDTO(registrationRqDTO,
                                                                                    HeaderDTO.Status.SUCCESS,
                                                                                    RegistrationRsDTO.RegistrationStatus.ALREADY_REGISTERED));
        }
        authCrudRepository.save(authModel);
        return ResponseEntity.ok(cookieGeneratorMapper.generateRegistrationRsDTO(registrationRqDTO,
                                                                                    HeaderDTO.Status.SUCCESS,
                                                                                    RegistrationRsDTO.RegistrationStatus.SUCCESS));
    }

    private boolean authorize(AuthorizationRqDTO authorizationRqDTO) {
        return authCrudRepository.findByLoginAndPassword(authorizationRqDTO.getLogin(), authorizationRqDTO.getPassword()) != null;
    }

    private AuthorizationRsDTO getSessionCookie(AuthorizationRqDTO authorizationRqDTO) {
        SessionModel sessionModel = sessionCookieCrudRepository.save(cookieGeneratorMapper.generateSessionModelEntity(authorizationRqDTO));
        return cookieGeneratorMapper.generateGetSessionCookieRsDTO(sessionModel, authorizationRqDTO);
    }
}
