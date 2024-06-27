package com.tarot.dto.request;

import org.springframework.lang.Nullable;

import java.util.List;

public record RequestTarotCard(
        List<TarotCardSearch> searchCards
          ) {
    public record TarotCardSearch(
              Integer cardId
            , @Nullable Boolean isReversed
            , @Nullable Character categoryCode
    ){
    }
}
