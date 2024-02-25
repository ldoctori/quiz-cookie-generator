package ru.quiz.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.quiz.generator.model.AuthModel;
@Repository
public interface AuthCrudRepository extends JpaRepository<AuthModel, Long> {

    AuthModel findByLogin(String login);

}
