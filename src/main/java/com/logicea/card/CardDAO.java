package com.logicea.card;

import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface CardDAO {
    List<Card> selectAllCards();

    List<Card> selectAllCardsByCreatedBy(String createdBy);

    Optional<Card> selectCardById(Integer id);

    Optional<Card> selectCardByIdCreatedBy(Integer id, String createdBy);

    void insertCard(Card card);

    boolean cardWithNameCreatedByExists(String name, String createdBy);

    boolean cardWithIdCreatedByExists(Integer id, String createdBy);

    void deleteCardById(Integer id);

    boolean cardWithIdExists(Integer id);

    void updateCard(Card update);

    List<Card> selectCardByCriteriaAndCreatedBy(String searchString, String createdBy, Sort sort);

    List<Card> selectCardByCriteria(String searchString, Sort sort);


}
