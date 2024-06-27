package com.tarot.dto.response;

import java.util.List;

public record ResponseTarotCardReadingMethod(
          Integer cardCount
        , List<ReadingMethod> readingMethods) {
        public record ReadingMethod(Integer methodId, String methodName, Integer methodOrder, String description){

        }

}
