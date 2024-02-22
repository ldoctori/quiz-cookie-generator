package ru.quiz.generator.app.rest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.quiz.generator.app.service.GetCookieService;
import ru.quiz.generator.dto.GetCookieRqDTO;
import ru.quiz.generator.utils.JsonUtil;

import javax.validation.Valid;
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class CookieRestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(GetCookieService.class);

    private final GetCookieService getCookieService;
    @PostMapping("getCookie")
    public ResponseEntity<?> getCookie(@Valid @RequestBody GetCookieRqDTO getCookieRqDTO) {

        LOGGER.info("Получен запрос getCookie на создание игровой куки. Тело запроса {}", JsonUtil.getPrettyJson(getCookieRqDTO));
        ResponseEntity<?> responseEntity = getCookieService.getCookieRsDTO(getCookieRqDTO);
        LOGGER.info(JsonUtil.getPrettyJson(responseEntity));
        return responseEntity;
    }
}
