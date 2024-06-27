package com.tarot.service;

import com.tarot.dto.request.RequestTarotCard;
import com.tarot.dto.response.*;
import com.tarot.entity.TarotCard;
import com.tarot.repository.TarotCardCategoryRepository;
import com.tarot.repository.TarotCardInterpretationRepository;
import com.tarot.repository.TarotCardKeyWordRepository;
import com.tarot.repository.TarotCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class TarotService {
    private final TarotCardRepository tarotCardRepository;
    private final TarotCardKeyWordRepository tarotCardKeyWordRepository;
    private final TarotCardCategoryRepository tarotCardCategoryRepository;
    private final TarotCardInterpretationRepository tarotCardInterpretationRepository;

    public List<TarotCard> getTarotCards(){
        return tarotCardRepository.findAll();
    }

    public ResponseTarotCardKeyword getTaroCard(int cardId){
        return tarotCardRepository.findTaroCardByCardId(cardId);
    }

    public List<ResponseTarotCardIntro> getTaroCardsIntro(){
        return tarotCardRepository.findTaroCardsIntro();
    }

    public List<ResponseTarotCardCategory> getCardCategories(){
        return tarotCardCategoryRepository.findAll().stream().map(t->new ResponseTarotCardCategory(t.getCategoryCode(), t.getCategoryName())).toList();
    }

    public List<ResponseTarotCard> getTaroCardKeyWords(List<RequestTarotCard.TarotCardSearch> params){
        return tarotCardRepository.findTaroCardKewords(params);
    }

    public List<ResponseTarotCardInterpretation> getTaroCardInterpretations(List<RequestTarotCard.TarotCardSearch> params){
        return tarotCardRepository.findTaroCardInterpretations(params);
    }

    //임의로 카드를 뽑는다(카드 갯수만큼)
    public List<ResponseTarotCardConsult> getTaroCardConsultsByRandom(int cardCount , Boolean isReverseOn
            , Character categoryCode){
        List<RequestTarotCard.TarotCardSearch> params = this.getRandomCards(cardCount, isReverseOn, categoryCode);
        System.out.println(params);
        List<ResponseTarotCardConsult> result = tarotCardRepository.findTaroCardConsults(params);
        System.out.println("result:"+result);
        return result;
    }

    //임의로 카드를 뽑는다(카드 갯수만큼)
    public List<ResponseTarotCardInterpretation> getTaroCardInterpretationsByRandom(int cardCount , Boolean isReverseOn
            , Character categoryCode){
        List<RequestTarotCard.TarotCardSearch> params = this.getRandomCards(cardCount, isReverseOn, categoryCode);
        System.out.println(params);
        List<ResponseTarotCardInterpretation> result = tarotCardRepository.findTaroCardInterpretations(params);
        System.out.println("result:"+result);
        return result;
    }

    private List<RequestTarotCard.TarotCardSearch> getRandomCards(int cardCount , Boolean isReverseOn
            , Character categoryCode){
        Random random = new Random();
        Set<Integer> cardSet=new HashSet<>();
        while(true) {
            //1 ~ 45 사이의 랜덤한 숫자 얻어내기
            int ranNum = random.nextInt(77);
            //얻어낸 숫자를 Set 에 저장하기
            cardSet.add(ranNum);
            //만일 lottoSet 의 size 가 6 이면 반복문 탈출
            if(cardSet.size() == cardCount) {
                break;
            }
        }

        return cardSet.stream().map(c->new RequestTarotCard.TarotCardSearch(
                c, (isReverseOn != null && isReverseOn) ? random.nextBoolean() : false, categoryCode)
        ).toList();
    }
}
