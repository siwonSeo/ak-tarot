package com.tarot.dto;

import java.util.List;

public record ResponseTarotCardConsult(
          Integer cardId
        , Integer cardNumber
        , String cardNumberName
        , String cardType
        , String cardName
        , boolean isReversed
        , List<String> interpretations) {
}
