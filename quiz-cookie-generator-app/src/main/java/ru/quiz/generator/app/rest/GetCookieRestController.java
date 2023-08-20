package ru.quiz.generator.app.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.quiz.generator.dto.GetCookieRqDTO;

@RestController("/")
public class GetCookieRestController {

    @PostMapping("getCookie")
    public String getCookie(@RequestBody GetCookieRqDTO getCookieRqDTO) {


        return null;
    }

}
