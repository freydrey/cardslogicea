package com.logicea.card;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CardJPADataAccessService implements CardDAO {

    private final CardRepository cardRepository;

    public CardJPADataAccessService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public List<Card> selectAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public List<Card> selectAllCardsByCreatedBy(String createdBy) {
        return cardRepository.findCardByCreatedBy(createdBy);
    }

    @Override
    public Optional<Card> selectCardById(Integer id) {
        return cardRepository.findById(id);
    }

    @Override
    public Optional<Card> selectCardByIdCreatedBy(Integer id, String createdBy) {
        return cardRepository.findCardByIdAndCreatedBy(id, createdBy);
    }

    @Override
    public void insertCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public boolean cardWithNameCreatedByExists(String email, String createdBy) {
        return cardRepository.existsCardByNameAndCreatedBy(email,createdBy);
    }

    @Override
    public boolean cardWithIdCreatedByExists(Integer id, String createdBy) {
        return cardRepository.existsCardByIdAndCreatedBy(id,createdBy);
    }

    @Override
    public void deleteCardById(Integer id) {
        cardRepository.deleteById(id);
    }

    @Override
    public boolean cardWithIdExists(Integer id) {
        return cardRepository.existsCardById(id);
    }

    @Override
    public void updateCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public List<Card> selectCardByCriteriaAndCreatedBy(String searchString, String userName, Sort sort) {
        return cardRepository.searchByCriteriaUserName(searchString, userName, sort);
    }

    @Override
    public List<Card> selectCardByCriteria(String searchString, Sort sort) {
        return cardRepository.searchByCriteriaOnly(searchString, sort);
    }


}
