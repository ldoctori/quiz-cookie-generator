package ru.quiz.generator.enums;

public enum TableFieldsEnum {

    ID("id"),
    GAME_COOKIE("game_cookie"),
    CREATION_TIME("creation_time"),
    SESSION_OF_PLAYER1("session_of_player1"),
    SESSION_OF_PLAYER2("session_of_player2");

    public final String label;
    TableFieldsEnum(String label) {
        this.label = label;
    }


}
