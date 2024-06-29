package com.tarot.service;

import com.tarot.dto.request.RequestTarotCard;
import com.tarot.dto.response.*;
import com.tarot.entity.TarotCard;
import com.tarot.entity.TarotCardReadingMethod;
import com.tarot.repository.TarotCardCategoryRepository;
import com.tarot.repository.TarotCardInterpretationRepository;
import com.tarot.repository.TarotCardKeyWordRepository;
import com.tarot.repository.TarotCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TarotService {
    private final TarotCardRepository tarotCardRepository;
    private final TarotCardKeyWordRepository tarotCardKeyWordRepository;
    private final TarotCardCategoryRepository tarotCardCategoryRepository;
    private final TarotCardInterpretationRepository tarotCardInterpretationRepository;

    public List<ResponseTarotCardRandom> getTarotCardsRandom(){
        return tarotCardRepository.findTaroCardRandom();
    }
    public ResponseTarotCardReadingMethod getTaroCardReadingMethod(int cardCount){
        return tarotCardRepository.findTaroCardReadingMethod(cardCount);
    }

    public List<ResponseTarotCardReadingMethod> getTaroCardReadingMethods(){
        return tarotCardRepository.findTaroCardReadingMethods();
    }

    public ResponseTarotCardReading getTaroCardReading(int cardCount){
        return tarotCardRepository.findTaroCardReading(cardCount);
    }

    public List<ResponseTarotCardReading> getTaroCardReadings(){
        return tarotCardRepository.findTaroCardReadings();
    }

    public ResponseTarotCardKeyword getTaroCard(int cardId){
        return tarotCardRepository.findTaroCardByCardId(cardId);
    }

    public List<ResponseTarotCardIntro> getTaroCardsIntro(){
        return tarotCardRepository.findTaroCardsIntro();
    }

    public ResponseTarotCardCategory getCardCategorie(Character categoryCode){
        return tarotCardCategoryRepository.findById(categoryCode).map(t->new ResponseTarotCardCategory(t.getCategoryCode(), t.getCategoryName())).get();
    }

    public List<ResponseTarotCardCategory> getCardCategories(){
        return tarotCardCategoryRepository.findAll().stream().map(t->new ResponseTarotCardCategory(t.getCategoryCode(), t.getCategoryName())).toList();
    }

    public List<ResponseTarotCard> getTaroCardKeyWords(List<RequestTarotCard.TarotCardSearch> params){
        return tarotCardRepository.findTaroCardKewords(params);
    }



    //수동으로로 카드를 뽑는다(카드 갯수만큼)
    public List<ResponseTarotCardConsult> getTaroCardConsultsBySelf(List<RequestTarotCard.TarotCardSearch> params){
        List<ResponseTarotCardConsult> queryResults = tarotCardRepository.findTaroCardConsults(params);// 쿼리 실행 결과
        return this.reOrderdResult(params, queryResults);
    }

    //임의로 카드를 뽑는다(카드 갯수만큼)
    public List<ResponseTarotCardConsult> getTaroCardConsultsByRandom(int cardCount , Boolean isReverseOn
            , Character categoryCode){
        List<RequestTarotCard.TarotCardSearch> params = this.getRandomCards(cardCount, isReverseOn, categoryCode);
        List<ResponseTarotCardConsult> queryResults = tarotCardRepository.findTaroCardConsults(params);// 쿼리 실행 결과
        return this.reOrderdResult(params, queryResults);
    }

    //입력 카드 순서와 일치하게 재정렬한다.
    private List<ResponseTarotCardConsult> reOrderdResult(List<RequestTarotCard.TarotCardSearch> params, List<ResponseTarotCardConsult> queryResults){
        Map<String, ResponseTarotCardConsult> resultMap = queryResults.stream()
                .collect(Collectors.toMap(
                        r -> r.cardId() + "-" + r.isReversed(),
                        Function.identity()
                ));

        List<ResponseTarotCardConsult> result = params.stream()
                .map(vo -> resultMap.get(vo.cardId() + "-" + vo.isReversed()))
                .collect(Collectors.toList());
        return result;
    }

    @Deprecated
    public List<ResponseTarotCardInterpretation> getTaroCardInterpretations(List<RequestTarotCard.TarotCardSearch> params){
        return tarotCardRepository.findTaroCardInterpretations(params);
    }

    //임의로 카드를 뽑는다(카드 갯수만큼)
    @Deprecated
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
