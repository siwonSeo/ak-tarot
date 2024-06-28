package com.tarot.dto.request;

import org.springframework.lang.Nullable;

import java.util.List;

public record RequestTarotCardSelect(
        int cardCount,
        Boolean isReverseOn,
        Character categoryCode,
        List<RequestTarotCard.TarotCardSearch> searchCards
          ) {

}
