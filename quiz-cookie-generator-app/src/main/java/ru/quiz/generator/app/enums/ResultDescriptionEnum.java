package ru.quiz.generator.app.enums;

public enum ResultDescriptionEnum {

    WRONG_LOGIN_OR_PASSOWRD("Не верное имя пользователя или пароль."),
    AUTHORIZATION_ERROR("Ошибка авторизации"),
    FAILURE_REGISTRATION("Ошибка регистранции."),
    ALREADY_REGISTERED("Пользователь с данным логином уже существует в системе."),
    SUCCESS_REGISTRATION("Регистрация завершена успешно."),
    REQUEST_ERROR("Ошибка запроса: "),
    EMPTY_HEADER("Поле header является обязательным."),
    NOT_VALID_REQUEST_ARGUMENT("Не валидное тело запроса.");


    public final String label;
    ResultDescriptionEnum(String label) {
        this.label = label;
    }


}
