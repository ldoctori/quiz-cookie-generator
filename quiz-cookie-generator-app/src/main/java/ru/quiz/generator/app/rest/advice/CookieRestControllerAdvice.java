package ru.quiz.generator.app.rest.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.input.CloseShieldInputStream;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import ru.quiz.generator.app.configuration.RequestContext;
import ru.quiz.generator.dto.header.HeaderDTO;
import ru.quiz.generator.dto.rs.AuthorizationRsDTO;
import ru.quiz.generator.utils.JsonUtil;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import static ru.quiz.generator.app.enums.ResultDescriptionEnum.*;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CookieRestControllerAdvice implements RequestBodyAdvice {

    private final ObjectMapper objectMapper;
    @Inject
    private RequestContext requestContext;

    @ExceptionHandler(value = AssertionError.class)
    public ResponseEntity<?> assertionErrorException(InputStream inputStream) {

        log.error(JsonUtil.getPrettyJson(requestContext.getRequestBody()));
        AuthorizationRsDTO authorizationRsDTO = new AuthorizationRsDTO().withErrorMessage(REQUEST_ERROR.label.concat(EMPTY_HEADER.label));
        return new ResponseEntity<>(authorizationRsDTO, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<?> httpMessageNotReadableException(InputStream inputStream) throws IllegalAccessException {

        log.error(JsonUtil.getPrettyJson(requestContext.getRequestBody()));
        AuthorizationRsDTO authorizationRsDTO = new AuthorizationRsDTO().withErrorMessage(REQUEST_ERROR.label.concat(NOT_VALID_REQUEST_ARGUMENT.label));
        try {
            authorizationRsDTO.withHeader((HeaderDTO) requestContext.getHeader());
            return new ResponseEntity<>(authorizationRsDTO, HttpStatus.BAD_REQUEST);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(authorizationRsDTO, HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

        CloseShieldInputStream csis = new CloseShieldInputStream(inputMessage.getBody());

        requestContext.setRequestBody(objectMapper.readValue(new String(csis.readAllBytes(), StandardCharsets.UTF_8), Object.class));
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return null;
    }
}
