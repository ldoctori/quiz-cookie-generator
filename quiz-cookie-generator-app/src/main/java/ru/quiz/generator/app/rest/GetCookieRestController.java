package ru.quiz.generator.app.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.quiz.generator.app.service.GetCookieService;
import ru.quiz.generator.dto.GetCookieRqDTO;

@RestController("/")
@RequiredArgsConstructor
public class GetCookieRestController {

    private final GetCookieService getCookieService;
    @PostMapping("getCookie")
    public String getCookie(@RequestBody GetCookieRqDTO getCookieRqDTO) {

        getCookieService.getCookieRsDTO(getCookieRqDTO);

        return null;
    }

}
