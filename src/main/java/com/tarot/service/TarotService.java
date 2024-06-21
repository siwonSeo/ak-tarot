package com.tarot.service;

import com.tarot.entity.TarotCard;
import com.tarot.repository.TarotCardKeyWordRepository;
import com.tarot.repository.TarotCardRepository;
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

@RequiredArgsConstructor
@Service
public class TarotService {
    private TarotCardRepository tarotCardRepository;
    private TarotCardKeyWordRepository tarotCardKeyWordRepository;

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
            System.out.println(jo);
        }
    }
}
