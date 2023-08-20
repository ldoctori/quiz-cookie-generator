package ru.quiz.generator.app.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.quiz.generator.dto.GetCookieRqDTO;
import ru.quiz.generator.dto.GetCookieRsDTO;
import ru.quiz.generator.model.CookieModel;
import ru.quiz.generator.repository.CookieCrudRepository;
import ru.quiz.generator.utils.JsonUtil;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class GetCookieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetCookieService.class);

    private final CookieCrudRepository cookieCrudRepository;

    public GetCookieRsDTO getCookieRsDTO(GetCookieRqDTO getCookieRqDTO) {

//        Optional<CookieModel> optionalCookieModel = cookieCrudRepository.findByPlayer1IsEmptyOrPlayer2IsEmpty();
//        if (optionalCookieModel.isPresent()) {
//            CookieModel cookieModel = optionalCookieModel.get();
//            cookieModel.getPlayer1() == null ? cookieModel.setPlayer1();
//        }

        Optional<CookieModel> cookieModel = cookieCrudRepository.findByPlayer1(getCookieRqDTO.getPlayerName(),
                                                                                getCookieRqDTO.getTheme());
        LOGGER.info("Вот же {}", JsonUtil.getPrettyJson(cookieModel.get()));
        return null;
    }

//    private void setNewPlayer(CookieModel cookieModel, String newPlayer) {
//        if (cookieModel.getPlayer1() == null) {
//            cookieModel.setPlayer1(newPlayer);
//        } else if (cookieModel.getPlayer2() == null) {
//            cookieModel.se
//        }
//
//    }

}
