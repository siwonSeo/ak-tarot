package com.tarot.repository;

import com.querydsl.core.Query;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tarot.dto.ResponseTarotCard;
import com.tarot.entity.QTarotCard;
import com.tarot.entity.QTarotCardKeyWord;
import com.tarot.entity.TarotCardKeyWord;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.processing.SQL;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

import static com.querydsl.core.group.GroupBy.*;

@RequiredArgsConstructor
@Repository
public class TarotCardRepositoryImpl implements TarotCardRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    QTarotCard tarotCard = QTarotCard.tarotCard;
    QTarotCardKeyWord tarotCardKeyWord = QTarotCardKeyWord.tarotCardKeyWord;

    @Override
    public List<ResponseTarotCard> findTaroCardKewords(List<Integer> params){
        JPAQuery<TarotCardKeyWord> query =
            queryFactory.selectFrom(tarotCardKeyWord)
                    .join(tarotCardKeyWord.tarotCard,tarotCard)
                    .where(eqCards(params))
            ;
        return query.transform(groupBy(tarotCardKeyWord.cardId).list(Projections.constructor(ResponseTarotCard.class,
                                tarotCard.cardId,
                                tarotCard.cardNumber,
                                tarotCard.cardNumberName,
                                tarotCard.cardType,
                                tarotCard.cardName,
                                list(Projections.constructor(ResponseTarotCard.KeywordInfo.class
                                        ,tarotCardKeyWord.keywordId
                                        ,tarotCardKeyWord.isReversed
                                        ,tarotCardKeyWord.keyword)))));
    }

    private BooleanExpression eqCards(List<Integer> params){
        if(params == null){
            return tarotCardKeyWord.cardId.isNull();
        }

        return tarotCardKeyWord.cardId.in(params);
    }
}
