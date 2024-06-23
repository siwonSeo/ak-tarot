package com.tarot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Category {
    private char categoryCode;
    private String categoryName;
    private List<Interpretation> interpretations;
}