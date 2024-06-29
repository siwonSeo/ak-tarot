package com.tarot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarot.dto.request.RequestTarotCard;
import com.tarot.dto.request.RequestTarotCardSelect;
import com.tarot.dto.response.ResponseTarotCard;
import com.tarot.dto.response.ResponseTarotCardInterpretation;
import com.tarot.dto.response.ResponseTarotCardKeyword;
import com.tarot.dto.response.ResponseTarotCardReading;
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
        model.addAttribute("readingMethods", tarotService.getTaroCardReadingMethods());
        return "main";
    }

    @GetMapping("/card/select/auto/result")
    public String auto(Model model,
                       @RequestParam(name = "cardCount") int cardCount
            , @RequestParam(name = "isReverseOn") Boolean isReverseOn //역방향 활성화 여부
            , @RequestParam(name = "categoryCode") Character categoryCode
    ) {
        model.addAttribute("isReverseOn", isReverseOn);
        model.addAttribute("category", tarotService.getCardCategorie(categoryCode));
        model.addAttribute("reading", tarotService.getTaroCardReading(cardCount));
        model.addAttribute("cards", tarotService.getTaroCardConsultsByRandom(cardCount, isReverseOn, categoryCode));
        return "selected";
    }

    //수동선택
    @GetMapping("/card/select/self")
    public String select(Model model,
                         @RequestParam(name = "cardCount") int cardCount
            , @RequestParam(name = "isReverseOn") Boolean isReverseOn //역방향 활성화 여부
            , @RequestParam(name = "categoryCode") Character categoryCode
    ) {
        model.addAttribute("cardCount", cardCount);
        model.addAttribute("isReverseOn", isReverseOn);
        model.addAttribute("categoryCode", categoryCode);
        model.addAttribute("cards", tarotService.getTarotCardsRandom());
        return "select";
    }

    @PostMapping("/card/select/self/result")
    public String selfResult(Model model,
         @RequestParam("cardCount") int cardCount,
         @RequestParam("isReverseOn") Boolean isReverseOn,
         @RequestParam("categoryCode") Character categoryCode,
         @RequestParam("searchCards") String searchCards)
    {
        ObjectMapper mapper = new ObjectMapper();
        List<RequestTarotCard.TarotCardSearch> cardsList;
        try {
            cardsList = mapper.readValue(searchCards, new TypeReference<List<RequestTarotCard.TarotCardSearch>>() {});
        } catch (JsonProcessingException e) {
            // 에러 처리
            return "error";
        }

        model.addAttribute("isReverseOn", isReverseOn);
        model.addAttribute("category", tarotService.getCardCategorie(categoryCode));
        model.addAttribute("reading", tarotService.getTaroCardReading(cardCount));
        model.addAttribute("cards", tarotService.getTaroCardConsultsBySelf(cardsList));
        return "selected";
    }

    @GetMapping("/card/intro")
    public String intro(Model model) {
        model.addAttribute("cardGroups", tarotService.getTaroCardsIntro());
        return "intro";
    }

    @GetMapping("/reading")
    public String reading(Model model) {
        model.addAttribute("readings", tarotService.getTaroCardReadings());
        return "reading";
    }

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
