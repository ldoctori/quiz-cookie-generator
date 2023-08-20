package ru.quiz.generator.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.quiz.generator")
public class QuizCookieGeneratorApp {
    public static void main(String[] args) {
        SpringApplication.run(QuizCookieGeneratorApp.class, args);
    }
}