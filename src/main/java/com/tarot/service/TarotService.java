package com.tarot.service;

import com.tarot.dto.RequestTarotCard;
import com.tarot.dto.ResponseTarotCard;
import com.tarot.dto.ResponseTarotCardInterpretation;
import com.tarot.entity.TarotCard;
import com.tarot.entity.TarotCardCategory;
import com.tarot.entity.TarotCardInterpretation;
import com.tarot.entity.TarotCardKeyWord;
import com.tarot.repository.TarotCardCategoryRepository;
import com.tarot.repository.TarotCardInterpretationRepository;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TarotService {
    private final TarotCardRepository tarotCardRepository;
    private final TarotCardKeyWordRepository tarotCardKeyWordRepository;
    private final TarotCardCategoryRepository tarotCardCategoryRepository;
    private final TarotCardInterpretationRepository tarotCardInterpretationRepository;

    @Transactional
    public void setDefaultData() throws IOException, ParseException {
        saveTarotCard();
        List<TarotCard> tarotCards = this.getTarotCards();

        //카테고리 저장
        try{
            this.saveCategory();
        }catch (Exception e){
            System.out.println("카테고리 exception:"+e.getMessage());
        }

        for (TarotCard tarotCard : tarotCards) {
            try{
                this.saveInterpretation(tarotCard);
//                tarotCardInterpretationRepository.saveAll()
            }catch (Exception e){
                System.out.println("saveInterpretation exception:"+e.getMessage());
            }
        }

    }


    public List<TarotCard> getTarotCards(){
        return tarotCardRepository.findAll();
    }

    public List<ResponseTarotCard> getTaroCardKeyWords(List<RequestTarotCard.TarotCardSearch> params){
        return tarotCardRepository.findTaroCardKewords(params);
    }

    public List<ResponseTarotCardInterpretation> getTaroCardInterpretations(List<RequestTarotCard.TarotCardSearch> params){
        return tarotCardRepository.findTaroCardInterpretations(params);
    }

    private void saveTarotCard() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        ClassPathResource resource = new ClassPathResource("data/기초데이터.json");
        Reader reader = new FileReader(resource.getFile(), StandardCharsets.UTF_8);
        JSONArray dateArray = (JSONArray) parser.parse(reader);
        System.out.println("dateArray:"+dateArray);
        for(Object obj : dateArray){
            JSONObject jo = (JSONObject)obj;
            System.out.println("jo:"+jo);
            TarotCard tarotCard = tarotCardRepository.save(new TarotCard(
                            Integer.parseInt(jo.get("cardId").toString())
                            ,Integer.parseInt(jo.get("cardNumber").toString())
                            ,jo.get("cardNumberName").toString()
                            ,jo.get("cardName").toString()
                            ,jo.get("cardType").toString()
                    )
            );

            System.out.println("tarotCard:"+tarotCard.getCardId());

            JSONArray list = (JSONArray)jo.get("keywords");
            tarotCardKeyWordRepository.saveAll(
                    list.stream().map(object->{
                                JSONObject jObject = (JSONObject)object;
                                return new TarotCardKeyWord(
                                        Integer.parseInt(jObject.get("keywordId").toString())
                                        ,tarotCard.getCardId()
                                        ,Boolean.parseBoolean(jObject.get("isReversed").toString())
                                        ,jObject.get("keyword").toString()
                                );
                            }
                    ).toList()
            );


        }
    }

    private void saveCategory(){
        tarotCardCategoryRepository.saveAll(new ArrayList<>(){{
            add(new TarotCardCategory('A',"사랑"));
            add(new TarotCardCategory('B',"커리어"));
            add(new TarotCardCategory('C',"금전"));
            add(new TarotCardCategory('D',"건강"));
            add(new TarotCardCategory('E',"공부"));
            add(new TarotCardCategory('F',"친구"));
            add(new TarotCardCategory('G',"가족"));
            add(new TarotCardCategory('Z',"기타"));
        }});
    }

    private void saveInterpretation(TarotCard tarotCard) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        ClassPathResource resource = new ClassPathResource("data/interpretation/"+tarotCard.getCardId()+".json");
        Reader reader = new FileReader(resource.getFile(), StandardCharsets.UTF_8);
        JSONObject jSONObject = (JSONObject) parser.parse(reader);

        JSONArray dateArray = (JSONArray)jSONObject.get("categories");
        List<TarotCardInterpretation> tarotCardCategorys = new ArrayList<>();

        System.out.println("#####dateArray:"+dateArray.toString());

        for(Object obj : dateArray){
            JSONObject jo = (JSONObject)obj;
            char categoryCode = jo.get("categoryCode").toString().charAt(0);
//            JSONArray array = (JSONArray)jo.get("interpretationContents");
            JSONArray array = (JSONArray)jo.get("interpretations");

            for(Object obj2 : array){
                JSONObject jo2 = (JSONObject)obj2;
                boolean isReversed = Boolean.parseBoolean(jo2.get("isReversed").toString());
                JSONArray array2 = (JSONArray)jo2.get("interpretationContents");

                Object oo= jo2.get("interpretationContents");
                System.out.println("#####oo:"+oo.toString());
                System.out.println("#####oo:"+oo.getClass());

                for(Object obj3 : array2){
                    System.out.println("#####obj3:"+obj3);
                    System.out.println("#####obj3:"+obj3.toString());
                    tarotCardCategorys.add(new TarotCardInterpretation(
                            tarotCard.getCardId()
                            ,categoryCode
                            ,isReversed
                            ,obj3.toString()
                    ));
                }
            }
        }

        tarotCardInterpretationRepository.saveAll(tarotCardCategorys);
    }

}
