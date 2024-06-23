package com.tarot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Interpretation{
    private boolean isReversed;
    private String content;
}
