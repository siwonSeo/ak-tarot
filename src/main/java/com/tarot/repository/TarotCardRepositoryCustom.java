package com.tarot.repository;

import com.tarot.dto.ResponseTarotCard;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarotCardRepositoryCustom {
    List<ResponseTarotCard> findTaroCardKewords(List<Integer> params);
}
