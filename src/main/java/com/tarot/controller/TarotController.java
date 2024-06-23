package com.tarot.controller;

import com.tarot.dto.RequestTarotCard;
import com.tarot.dto.ResponseTarotCard;
import com.tarot.dto.ResponseTarotCardInterpretation;
import com.tarot.entity.TarotCard;
import com.tarot.service.TarotService;
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

    @GetMapping("/main")
    public ModelAndView abc1(ModelAndView model) {
//        model.setViewName("card");
//        return model;
        return new ModelAndView("card");
    }

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
}
