package com.tarot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarot.dto.request.RequestTarotCard;
import com.tarot.dto.response.ResponseTarotCard;
import com.tarot.dto.response.ResponseTarotCardInterpretation;
import com.tarot.dto.response.ResponseTarotCardKeyword;
import com.tarot.service.TarotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class TarotApiController {
    private final TarotService tarotService;
//    private final AnthropicApiService anthropicApiService;

    @GetMapping("/card/{cardId}")
    public ResponseEntity<ResponseTarotCardKeyword> getTaroCard(@PathVariable("cardId") int cardId) {
        return new ResponseEntity<>(tarotService.getTaroCard(cardId), HttpStatus.OK);
    }

    /*
    @GetMapping("/card/intro")
    public ResponseEntity<List<ResponseTarotCardIntro>> intro() {
//        model.addAttribute("cards", tarotService.getTarotCards());
        return new ResponseEntity<>(tarotService.getTaroCardsIntro(), HttpStatus.OK);
    }

     */
    /*
    @GetMapping("/interpretation/{cardCount}")
    public String interpretation(Model model, @RequestParam int cardCount) {
        model.addAttribute("cardInterpretations", tarotService.getTaroCardInterpretationsByRandom(cardCount));
        return "interpretation";
    }

     */

    @PostMapping("/card/keyword/search")
    public ResponseEntity<List<ResponseTarotCard>> getTaroCardKeyWords(@RequestBody RequestTarotCard param) {
        return new ResponseEntity<>(tarotService.getTaroCardKeyWords(param.searchCards()), HttpStatus.OK);
    }

    @PostMapping("/card/interpretation/search")
    @Deprecated
    public ResponseEntity<List<ResponseTarotCardInterpretation>> getTaroCardInterpretations(@RequestBody RequestTarotCard param) {
        return new ResponseEntity<>(tarotService.getTaroCardInterpretations(param.searchCards()), HttpStatus.OK);
    }

    @GetMapping("/card/interpretation/{cardCount}")
    @Deprecated
    public ResponseEntity<List<ResponseTarotCardInterpretation>> getTaroCardInterpretations(
            @PathVariable("cardCount") int cardCount
            , @RequestParam(name = "isReverseOn", required = false) Boolean isReverseOn //역방향 활성화 여부
            , @RequestParam(name = "categoryCode", required = false) Character categoryCode

    ) {
        return new ResponseEntity<>(tarotService.getTaroCardInterpretationsByRandom(cardCount, isReverseOn, categoryCode), HttpStatus.OK);
    }

    /*
    @PostMapping("/api/test")
    public ResponseEntity<String> apiTest(@RequestBody String prompt
    ) {
        return new ResponseEntity<>(anthropicApiService.sendRequest(prompt), HttpStatus.OK);
    }

     */
}
