package ru.quiz.generator.exception;

public class TableUpdateException extends RuntimeException{
    public TableUpdateException(){
        super("Ошибка добавления информации в таблицу!");
    }
}
