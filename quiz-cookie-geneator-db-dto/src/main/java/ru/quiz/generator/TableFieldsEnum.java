package ru.quiz.generator;

public enum TableFieldsEnum {

    ID("id"),
    COOKIE("cookie"),
    CREATION_TIME("creationTime"),
    PLAYER1("player1"),
    PLAYER2("player2");

    public final String label;
    private TableFieldsEnum(String label) {
        this.label = label;
    }


}
