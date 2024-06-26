package com.tarot.repository;

import com.tarot.dto.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarotCardRepositoryCustom {
    List<ResponseTarotCard> findTaroCardByCardId(int cardId);
    List<ResponseTarotCard> findTaroCardKewords(List<RequestTarotCard.TarotCardSearch> params);
    List<ResponseTarotCardInterpretation> findTaroCardInterpretations(List<RequestTarotCard.TarotCardSearch> params);
    List<ResponseTarotCardConsult> findTaroCardConsults(List<RequestTarotCard.TarotCardSearch> params);
    List<ResponseTarotCardIntro> findTaroCardsIntro();
}
