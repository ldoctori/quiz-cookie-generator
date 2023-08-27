package ru.quiz.generator.app.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.quiz.generator.app.mapper.CookieGeneratorMapper;
import ru.quiz.generator.dto.GetCookieRqDTO;
import ru.quiz.generator.dto.GetCookieRsDTO;
import ru.quiz.generator.dto.model.CookieModelWithEnemyDTO;
import ru.quiz.generator.exception.TableUpdateException;
import ru.quiz.generator.repository.CookieCrudRepository;
import ru.quiz.generator.utils.JsonUtil;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class GetCookieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetCookieService.class);

    private final CookieCrudRepository cookieCrudRepository;
    private final CookieGeneratorMapper cookieGeneratorMapper;

    public ResponseEntity<?> getCookieRsDTO(GetCookieRqDTO getCookieRqDTO) {

        try {
            Optional<?> optionalCookieModel = cookieCrudRepository.getCookie(getCookieRqDTO);
            LOGGER.info(JsonUtil.getPrettyJson(optionalCookieModel.get()));
            CookieModelWithEnemyDTO cookieModel = (CookieModelWithEnemyDTO) optionalCookieModel.get();
            GetCookieRsDTO getCookieRsDTO = cookieGeneratorMapper.generateCookieRsDTO(cookieModel, getCookieRqDTO);
            return ResponseEntity.ok(getCookieRsDTO);
        } catch (DataAccessException | TableUpdateException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
