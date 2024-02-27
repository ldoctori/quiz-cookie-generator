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
import ru.quiz.generator.dto.rq.RegistrationRqDTO;
import ru.quiz.generator.dto.rq.GetGameCookieRqDTO;
import ru.quiz.generator.dto.rq.AuthorizationRqDTO;
import ru.quiz.generator.utils.JsonUtil;

import javax.inject.Inject;
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

    @PostMapping("authorizeAndGetSessionCookie")
    public ResponseEntity<?> authorizeAndGetSessionCookie(@Valid @RequestBody AuthorizationRqDTO authorizationRqDTO) {

        LOGGER.info("получен запрос на создание сессионной куки. Тело запроса {}", JsonUtil.getPrettyJson(authorizationRqDTO));
        ResponseEntity<?> responseEntity = getCookieService.authorizeAndGetSessionCookie(authorizationRqDTO);
        return responseEntity;
    }

    @PostMapping("registration")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationRqDTO registrationRqDTO) {
        LOGGER.info("получен запрос на регистрацию. Тело запроса {}", JsonUtil.getPrettyJson(registrationRqDTO));
        return getCookieService.registration(registrationRqDTO);
    }
}
