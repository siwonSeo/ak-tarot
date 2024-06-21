package com.tarot.service;

import com.tarot.dto.ResponseTarotCard;
import com.tarot.entity.TarotCard;
import com.tarot.entity.TarotCardKeyWord;
import com.tarot.repository.TarotCardKeyWordRepository;
import com.tarot.repository.TarotCardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TarotService {
    private final TarotCardRepository tarotCardRepository;
    private final TarotCardKeyWordRepository tarotCardKeyWordRepository;

    @Transactional
    public void setDefaultData() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        ClassPathResource resource = new ClassPathResource("data/기초데이터.json");
        Reader reader = new FileReader(resource.getFile());
        JSONArray dateArray = (JSONArray) parser.parse(reader);

        for(Object obj : dateArray){
            JSONObject jo = (JSONObject)obj;
            TarotCard tarotCard = tarotCardRepository.save(new TarotCard(
                    Integer.parseInt(jo.get("cardId").toString())
                            ,Integer.parseInt(jo.get("cardNumber").toString())
                            ,jo.get("cardNumberName").toString()
                            ,jo.get("cardName").toString()
                            ,jo.get("cardType").toString()
                    )
            );

            JSONArray list = (JSONArray)jo.get("keywords");
            tarotCardKeyWordRepository.saveAll(
                list.stream().map(object->{
                    JSONObject jObject = (JSONObject)object;
                    return new TarotCardKeyWord(
                         tarotCard.getCardId()
                        ,Boolean.parseBoolean(jObject.get("isReversed").toString())
                        ,jObject.get("keyword").toString()
                    );
                    }
                ).toList()
            );
//
//            tarotCardKeyWordRepository.saveAll(new TarotCard(
//                            Integer.parseInt(jo.get("cardId").toString())
//                            ,Integer.parseInt(jo.get("cardNumber").toString())
//                            ,jo.get("cardNumberName").toString()
//                            ,jo.get("cardName").toString()
//                            ,jo.get("cardType").toString()
//                    )
//            );

            System.out.println(jo);
        }


    }

    public List<TarotCard> getTarotCards(){
        return tarotCardRepository.findAll();
    }

    public List<ResponseTarotCard> getTaroCardKeyWords(List<Integer> params){
        return tarotCardRepository.findTaroCardKewords(params);
    }
}
