package ru.quiz.generator.app.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "ru.quiz.generator")
@EnableJpaRepositories(basePackages = "ru.quiz.generator")
public class CookieGeneratorConfiguration {
}
