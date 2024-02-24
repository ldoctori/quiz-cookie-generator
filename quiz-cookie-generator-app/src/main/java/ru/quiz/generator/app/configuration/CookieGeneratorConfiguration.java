package ru.quiz.generator.app.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("ru.quiz.generator.repository")
@EntityScan("ru.quiz.generator.model")
public class CookieGeneratorConfiguration {
}
