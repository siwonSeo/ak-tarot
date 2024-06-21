package com.tarot.controller;

import com.tarot.dto.ResponseTarotCard;
import com.tarot.entity.TarotCard;
import com.tarot.service.TarotService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/tarot")
@RestController
public class TarotController {
    private final TarotService tarotService;

    @GetMapping("/cards")
    public ResponseEntity<List<TarotCard>> getTaroCards(){
        return new ResponseEntity<>(tarotService.getTarotCards(), HttpStatus.OK);
    }

    @GetMapping("/card/keywords")
    public ResponseEntity<List<ResponseTarotCard>> getTaroCardKeyWords(@RequestParam(required = false) List<Integer> params){
        return new ResponseEntity<>(tarotService.getTaroCardKeyWords(params), HttpStatus.OK);
    }
}
