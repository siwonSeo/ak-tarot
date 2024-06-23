package com.tarot.repository;

import com.tarot.entity.TarotCard;
import com.tarot.entity.TarotCardCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarotCardCategoryRepository extends JpaRepository<TarotCardCategory, Character>{
}
