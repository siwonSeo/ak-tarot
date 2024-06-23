package com.tarot.dto;

import java.util.List;

public record RequestTarotCard(
        List<TarotCardSearch> searchCards
          ) {
    public record TarotCardSearch(
              Integer cardId
            , Boolean isReversed){
    }
}
