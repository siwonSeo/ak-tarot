package com.tarot.controller;

import com.tarot.service.TarotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/szs")
@RestController
public class TarotController {
    private final TarotService tarotService;
}
