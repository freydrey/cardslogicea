package com.logicea.card;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CardRepository
        extends JpaRepository<Card, Integer> {

    boolean existsCardByNameAndCreatedBy(String name, String createdBy);
    boolean existsCardByIdAndCreatedBy(Integer id, String createdBy);
    boolean existsCardById(Integer id);
    Optional<Card> findCardByIdAndCreatedBy(Integer id, String createdBy);

    List<Card> findCardByCreatedBy(String createdBy);

    @Query("SELECT c FROM Card c WHERE (c.name LIKE %:searchString% OR c.color LIKE %:searchString% OR c.status LIKE %:searchString% OR DATE(c.createdAt) = DATE(:searchString)) AND c.createdBy LIKE %:userName%")
    List<Card> searchByCriteriaUserName(String searchString, String userName, Sort sort);

    @Query("SELECT c FROM Card c WHERE c.name LIKE %:searchString% OR c.color LIKE %:searchString% OR c.status LIKE %:searchString% OR DATE(c.createdAt) = DATE(:searchString)")
    List<Card> searchByCriteriaOnly(String searchString, Sort sort);

}
