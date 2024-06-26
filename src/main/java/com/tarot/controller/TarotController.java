package com.tarot.controller;

import com.tarot.dto.RequestTarotCard;
import com.tarot.dto.ResponseTarotCard;
import com.tarot.dto.ResponseTarotCardInterpretation;
import com.tarot.dto.ResponseTarotCardIntro;
import com.tarot.entity.TarotCard;
import com.tarot.service.TarotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
//@RestController
@Controller
public class TarotController {
    private final TarotService tarotService;
//    private final AnthropicApiService anthropicApiService;

    @GetMapping("/")
    public String index(Model model) {
//        model.addAttribute("cards", tarotService.getTarotCards());
        model.addAttribute("categories", tarotService.getCardCategories());
        return "main";
    }

    @GetMapping("/card/selected")
    public String selected(Model model,
             @RequestParam(name="cardCount") int cardCount
            ,@RequestParam(name="isReverseOn") Boolean isReverseOn //역방향 활성화 여부
            ,@RequestParam(name="categoryCode") Character categoryCode
    ) {
//        model.addAttribute("cards", tarotService.getTarotCards());

        model.addAttribute("cards", tarotService.getTaroCardConsultsByRandom(cardCount,isReverseOn,categoryCode));
        return "selected";
    }

    @GetMapping("/card/intro")
    public String intro(Model model) {
//        model.addAttribute("cards", tarotService.getTarotCards());
        model.addAttribute("cardGroups", tarotService.getTaroCardsIntro());
        return "intro";
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

    @GetMapping("/cards")
    public ResponseEntity<List<TarotCard>> getTaroCards(){
        return new ResponseEntity<>(tarotService.getTarotCards(), HttpStatus.OK);
    }

    @PostMapping("/card/keyword/search")
    public ResponseEntity<List<ResponseTarotCard>> getTaroCardKeyWords(@RequestBody RequestTarotCard param){
        return new ResponseEntity<>(tarotService.getTaroCardKeyWords(param.searchCards()), HttpStatus.OK);
    }

    @PostMapping("/card/interpretation/search")
    public ResponseEntity<List<ResponseTarotCardInterpretation>> getTaroCardInterpretations(@RequestBody RequestTarotCard param){
        return new ResponseEntity<>(tarotService.getTaroCardInterpretations(param.searchCards()), HttpStatus.OK);
    }

    @GetMapping("/card/interpretation/{cardCount}")
    public ResponseEntity<List<ResponseTarotCardInterpretation>> getTaroCardInterpretations(
             @PathVariable("cardCount") int cardCount
            ,@RequestParam(name="isReverseOn", required = false) Boolean isReverseOn //역방향 활성화 여부
            ,@RequestParam(name="categoryCode", required = false) Character categoryCode

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
