package com.tarot.repository;

import com.tarot.dto.RequestTarotCard;
import com.tarot.dto.ResponseTarotCard;
import com.tarot.dto.ResponseTarotCardInterpretation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarotCardRepositoryCustom {
    List<ResponseTarotCard> findTaroCardKewords(List<RequestTarotCard.TarotCardSearch> params);
    List<ResponseTarotCardInterpretation> findTaroCardInterpretations(List<RequestTarotCard.TarotCardSearch> params);
}
