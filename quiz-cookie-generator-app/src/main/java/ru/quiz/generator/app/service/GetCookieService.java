package ru.quiz.generator.app.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.quiz.generator.app.mapper.CookieGeneratorMapper;
import ru.quiz.generator.dto.*;
import ru.quiz.generator.dto.header.HeaderDTO;
import ru.quiz.generator.dto.model.CookieModelWithEnemyDTO;
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

        try {
            Optional<?> optionalCookieModel = gameCookieCrudRepository.getCookie(getGameCookieRqDTO);
            LOGGER.info(JsonUtil.getPrettyJson(optionalCookieModel.get()));
            CookieModelWithEnemyDTO cookieModel = (CookieModelWithEnemyDTO) optionalCookieModel.get();
            GetGameCookieRsDTO getGameCookieRsDTO = cookieGeneratorMapper.generateCookieRsDTO(cookieModel, getGameCookieRqDTO);
            return ResponseEntity.ok(getGameCookieRsDTO);
        } catch (DataAccessException | TableUpdateException e) {
            LOGGER.error(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getSessionCookieRsDTO(GetSessionCookieRqDTO getSessionCookieRqDTO) {

        try {
            SessionModel sessionModel = sessionCookieCrudRepository.save(cookieGeneratorMapper.generateSessionModelEntity(getSessionCookieRqDTO));
            GetSessionCookieRsDTO getSessionCookieRsDTO = cookieGeneratorMapper.generateGetSessionCookieRsDTO(sessionModel, getSessionCookieRqDTO);
            return ResponseEntity.ok(getSessionCookieRsDTO);
        } catch (PersistenceException e) {
            LOGGER.error(e.toString());
            return ResponseEntity.ok("NOOOO");
        }
    }

    public ResponseEntity<?> registration(RegistrationRqDTO registrationRqDTO) {

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
}
