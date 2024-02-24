package ru.quiz.generator.app.rest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.quiz.generator.app.service.GetCookieService;
import ru.quiz.generator.dto.AuthRqDTO;
import ru.quiz.generator.dto.GetGameCookieRqDTO;
import ru.quiz.generator.dto.GetSessionCookieRqDTO;
import ru.quiz.generator.utils.JsonUtil;

import javax.validation.Valid;
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class CookieRestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(GetCookieService.class);

    private final GetCookieService getCookieService;
    @PostMapping("getGameCookie")
    public ResponseEntity<?> getGameCookie(@Valid @RequestBody GetGameCookieRqDTO getGameCookieRqDTO) {

        LOGGER.info("Получен запрос getCookie на создание игровой куки. Тело запроса {}", JsonUtil.getPrettyJson(getGameCookieRqDTO));
        ResponseEntity<?> responseEntity = getCookieService.getGameCookieRsDTO(getGameCookieRqDTO);
        LOGGER.info(JsonUtil.getPrettyJson(responseEntity));
        return responseEntity;
    }

    @PostMapping("getSessionCookie")
    public ResponseEntity<?> getSessionCookie(@Valid @RequestBody GetSessionCookieRqDTO getSessionCookieRqDTO) {
        LOGGER.info("получен запрос на создание сессионной куки. Тело запроса {}", JsonUtil.getPrettyJson(getSessionCookieRqDTO));
        ResponseEntity<?> responseEntity = getCookieService.getSessionCookieRsDTO(getSessionCookieRqDTO);
        return responseEntity;
    }

    @PostMapping("registration")
    public ResponseEntity<?> registration(@Valid @RequestBody AuthRqDTO authRqDTO) {
        LOGGER.info("получен запрос на регистрацию. Тело запроса {}", JsonUtil.getPrettyJson(authRqDTO));
        return null;
    }
}
