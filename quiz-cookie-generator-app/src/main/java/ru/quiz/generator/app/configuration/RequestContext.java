package ru.quiz.generator.app.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.annotation.RequestScope;
import ru.quiz.generator.dto.header.HeaderDTO;

import javax.annotation.ManagedBean;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

@ManagedBean
@RequestScope
@Getter
@Setter
public class RequestContext<T> {
    private T requestBody;

    public T getHeader() throws IllegalAccessException {
        Class<?> clazz = requestBody.getClass();



        Optional<Field> headerField = Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.getName().equals("header")).findAny();
        if (headerField.isEmpty())
            return null;
        ReflectionUtils.makeAccessible(headerField.get());
        T header = (T) headerField.get().get(requestBody);
        return header;
    }
}
