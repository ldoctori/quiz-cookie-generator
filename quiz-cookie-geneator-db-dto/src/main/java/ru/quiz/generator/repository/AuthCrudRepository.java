package ru.quiz.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.quiz.generator.model.SessionModel;

@Repository
public interface AuthCrudRepository extends JpaRepository<SessionModel, Long> {
}
