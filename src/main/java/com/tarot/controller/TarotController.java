package com.tarot.controller;

import com.tarot.dto.RequestTarotCard;
import com.tarot.dto.ResponseTarotCard;
import com.tarot.dto.ResponseTarotCardInterpretation;
import com.tarot.entity.TarotCard;
import com.tarot.service.TarotService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
//@RestController
@Controller
public class TarotController {
    private final TarotService tarotService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("cards", tarotService.getTarotCards());
        return "index";
    }

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
}
