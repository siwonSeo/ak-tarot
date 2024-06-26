package com.tarot.dto;

import java.util.List;

public record ResponseTarotCardKeword(
          Integer cardId
        , Integer cardNumber
        , String cardNumberName
        , String cardType
        , String cardName
        , KeywordInfo keywords) {
        public record KeywordInfo(boolean isReversed, List<String> keywords){

        }

}
