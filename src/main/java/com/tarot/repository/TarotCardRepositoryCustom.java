package com.tarot.repository;

import com.tarot.dto.request.RequestTarotCard;
import com.tarot.dto.response.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarotCardRepositoryCustom {
    ResponseTarotCardKeyword findTaroCardByCardId(int cardId);

    List<ResponseTarotCardReading> findTaroCardReading();
    List<ResponseTarotCardReadingMethod> findTaroCardReadingMethod();
    List<ResponseTarotCard> findTaroCardKewords(List<RequestTarotCard.TarotCardSearch> params);
    List<ResponseTarotCardInterpretation> findTaroCardInterpretations(List<RequestTarotCard.TarotCardSearch> params);
    List<ResponseTarotCardConsult> findTaroCardConsults(List<RequestTarotCard.TarotCardSearch> params);
    List<ResponseTarotCardIntro> findTaroCardsIntro();
}
