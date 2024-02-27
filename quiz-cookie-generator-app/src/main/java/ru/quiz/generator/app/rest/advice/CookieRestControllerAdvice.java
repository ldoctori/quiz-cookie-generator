package ru.quiz.generator.app.rest.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.quiz.generator.dto.rs.AuthorizationRsDTO;

import javax.inject.Inject;
import java.io.*;

import static ru.quiz.generator.app.enums.ResultDescriptionEnum.*;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CookieRestControllerAdvice {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(value = AssertionError.class)
    public ResponseEntity<?> assertionErrorException(InputStream inputStreams)  {

        AuthorizationRsDTO authorizationRsDTO = new AuthorizationRsDTO().withErrorMessage(REQUEST_ERROR.label.concat(EMPTY_HEADER.label));
        return new ResponseEntity<>(authorizationRsDTO, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<?> httpMessageNotReadableException(InputStream inputStream) throws IllegalAccessException {

        AuthorizationRsDTO authorizationRsDTO = new AuthorizationRsDTO().withErrorMessage(REQUEST_ERROR.label.concat(NOT_VALID_REQUEST_ARGUMENT.label));
        return new ResponseEntity<>(authorizationRsDTO, HttpStatus.BAD_REQUEST);

    }
}
