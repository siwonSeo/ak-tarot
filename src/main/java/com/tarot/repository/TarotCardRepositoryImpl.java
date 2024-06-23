package com.tarot.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tarot.dto.*;
import com.tarot.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.*;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
@Repository
public class TarotCardRepositoryImpl implements TarotCardRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    QTarotCard tarotCard = QTarotCard.tarotCard;
    QTarotCardKeyWord tarotCardKeyWord = QTarotCardKeyWord.tarotCardKeyWord;
    QTarotCardCategory tarotCardCategory = QTarotCardCategory.tarotCardCategory;
    QTarotCardInterpretation tarotCardInterpretation = QTarotCardInterpretation.tarotCardInterpretation;

    @Override
    public List<ResponseTarotCard> findTaroCardKewords(List<RequestTarotCard.TarotCardSearch> params){
        JPAQuery<TarotCardKeyWord> query =
            queryFactory.selectFrom(tarotCardKeyWord)
                    .join(tarotCardKeyWord.tarotCard,tarotCard)
                    .where(eqCardKeyWords(params))
            ;
        return query.transform(groupBy(tarotCardKeyWord.cardId)
                    .list(
                        constructor(
                                ResponseTarotCard.class,
                                tarotCard.cardId,
                                tarotCard.cardNumber,
                                tarotCard.cardNumberName,
                                tarotCard.cardType,
                                tarotCard.cardName,
                                list(constructor(ResponseTarotCard.KeywordInfo.class
                                        ,tarotCardKeyWord.keywordId
                                        ,tarotCardKeyWord.isReversed
                                        ,tarotCardKeyWord.keyword)
                                )
                        )
                    )
                );
    }

    @Override
    public List<ResponseTarotCardInterpretation> findTaroCardInterpretations(List<RequestTarotCard.TarotCardSearch> params) {
        /*
        JPAQuery<TarotCardInterpretation> query = queryFactory.selectFrom(tarotCardInterpretation)
                .join(tarotCardInterpretation.tarotCard,tarotCard)
                .join(tarotCardCategory).on(tarotCardCategory.categoryCode.eq(tarotCardInterpretation.categoryCode))
                .where(eqCardInterpretations(params))
                ;

        return query.transform(groupBy(tarotCardInterpretation.cardId)
                    .list(
                        Projections.constructor(ResponseTarotCardInterpretation.class,
                        tarotCard.cardId,
                        tarotCard.cardNumber,
                        tarotCard.cardNumberName,
                        tarotCard.cardType,
                        tarotCard.cardName,
                        GroupBy.list(Projections.constructor(ResponseTarotCardInterpretation.Category.class
                                ,tarotCardInterpretation.categoryCode
                                ,tarotCardCategory.categoryName
                                ,GroupBy.list(Projections.constructor(ResponseTarotCardInterpretation.Category.Interpretation.class
                                    ,tarotCardInterpretation.isReversed
                                    ,GroupBy.list(tarotCardInterpretation.content)
                                ))
                            ))

                        )
                    )
                );
        //중첩 list가 오류남.. java.lang.ClassCastException: Cannot cast java.lang.Boolean to java.util.List
         */

        List<Tuple> results = queryFactory
                .select(tarotCard.cardId,
                        tarotCard.cardNumber,
                        tarotCard.cardNumberName,
                        tarotCard.cardType,
                        tarotCard.cardName,
                        tarotCardInterpretation.categoryCode,
                        tarotCardCategory.categoryName,
                        tarotCardInterpretation.isReversed,
                        tarotCardInterpretation.content)
                .from(tarotCardInterpretation)
                .join(tarotCardInterpretation.tarotCard, tarotCard)
                .join(tarotCardCategory).on(tarotCardCategory.categoryCode.eq(tarotCardInterpretation.categoryCode))
                .where(eqCardInterpretations(params))
                .fetch();

        Map<Integer, ResponseTarotCardInterpretation> cardMap = new HashMap<>();

        for (Tuple tuple : results) {
            Integer cardId = tuple.get(tarotCard.cardId);
            ResponseTarotCardInterpretation card = cardMap.computeIfAbsent(cardId,
                    k -> new ResponseTarotCardInterpretation(
                            cardId,
                            tuple.get(tarotCard.cardNumber),
                            tuple.get(tarotCard.cardNumberName),
                            tuple.get(tarotCard.cardType),
                            tuple.get(tarotCard.cardName),
                            new ArrayList<>()
                    )
            );

            char categoryCode = tuple.get(tarotCardInterpretation.categoryCode);
            String categoryName = tuple.get(tarotCardCategory.categoryName);
            boolean isReversed = tuple.get(tarotCardInterpretation.isReversed);
            String content = tuple.get(tarotCardInterpretation.content);

            ResponseTarotCardInterpretation.Category category = card.categories().stream()
                    .filter(c -> c.categoryCode() == categoryCode)
                    .findFirst()
                    .orElseGet(() -> {
                        ResponseTarotCardInterpretation.Category newCategory = new ResponseTarotCardInterpretation.Category(
                                categoryCode,
                                categoryName,
                                new ArrayList<>()
                        );
                        card.categories().add(newCategory);
                        return newCategory;
                    });

            ResponseTarotCardInterpretation.Category.Interpretation interpretation = category.interpretations().stream()
                    .filter(i -> i.isReversed() == isReversed)
                    .findFirst()
                    .orElseGet(() -> {
                        ResponseTarotCardInterpretation.Category.Interpretation newInterpretation = new ResponseTarotCardInterpretation.Category.Interpretation(
                                isReversed,
                                new ArrayList<>()
                        );
                        category.interpretations().add(newInterpretation);
                        return newInterpretation;
                    });

            interpretation.contents().add(content);
        }

        List<ResponseTarotCardInterpretation> finalResult = new ArrayList<>(cardMap.values());

        return finalResult;


    }


    private BooleanExpression eqCardKeyWords(List<RequestTarotCard.TarotCardSearch> params){
        return params.stream()
                .map(p->tarotCardKeyWord.cardId.eq(p.cardId()).and(tarotCardKeyWord.isReversed.eq(p.isReversed())))
                .reduce(BooleanExpression::or)
                .orElseGet(tarotCardKeyWord.cardId::isNull);
    }
    private BooleanExpression eqCardInterpretations(List<RequestTarotCard.TarotCardSearch> params){
        return params.stream()
                .map(p->tarotCardInterpretation.cardId.eq(p.cardId()).and(tarotCardInterpretation.isReversed.eq(p.isReversed())))
                .reduce(BooleanExpression::or)
                .orElseGet(tarotCardInterpretation.cardId::isNull);
    }
}
