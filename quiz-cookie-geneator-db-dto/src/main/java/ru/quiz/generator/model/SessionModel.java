package ru.quiz.generator.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(schema = "session_cookie_schema", name = "session_cookies")
public class SessionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_cookie")
    private String sessionCookie;

    @Column(name = "login")
    private String login;

    @Column(name = "creation_time")
    private Date creationTime;
}
