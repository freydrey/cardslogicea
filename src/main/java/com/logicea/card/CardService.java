package com.logicea.card;

import com.logicea.exception.*;
import com.logicea.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.*;

@Service
public class CardService {

    private final CardDAO cardDAO;
    private final JWTUtil jwtUtil;

    public CardService(CardDAO cardDAO, JWTUtil jwtUtil) {
        this.cardDAO = cardDAO;
        this.jwtUtil = jwtUtil;
    }

    public List<Card> getAllCards(String token){

        List<String> roles = new ArrayList<>();
        String createdBy = jwtUtil.getSubject(token.substring(7));
        System.out.println("token " + token.substring(7));
        Collection<GrantedAuthority> authorities = jwtUtil.extractAuthorities(token.substring(7));
        System.out.println("size " + authorities.size() + " " + token.substring(7));
        // Iterate over the collection of GrantedAuthority objects
        for (GrantedAuthority authority : authorities) {
            // Access individual authority/role
            String authorityName = authority.getAuthority();
            System.out.println("Authority: " + authorityName);
            roles.add(authorityName);
        }

        if(roles.contains(RolesEnum.Admin.toString())){
            List<Card> cardsList = cardDAO.selectAllCards();
            if(cardsList.isEmpty()){
                throw new ResourceNotFoundException(
                        "cards NOT found");
            }else{
                return cardsList;
            }
        }else{
            List<Card> cardsList = cardDAO.selectAllCardsByCreatedBy(createdBy);
            if(cardsList.isEmpty()){
                throw new ResourceNotFoundException(
                        "cards NOT found");
            }else{
                return cardsList;
            }
        }

    }

    public Card getCardById(Integer id){
        return cardDAO.selectCardById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "card with id [%s] NOT found"
                                .formatted(id)));
    }

    public Card getCardByIdCreatedBy(Integer id, String token){
        List<String> roles = new ArrayList<>();
        String createdBy = jwtUtil.getSubject(token.substring(7));

        Collection<GrantedAuthority> authorities = jwtUtil.extractAuthorities(token.substring(7));

        // Iterate over the collection of GrantedAuthority objects
        for (GrantedAuthority authority : authorities) {
            // Access individual authority/role
            String authorityName = authority.getAuthority();
            System.out.println("Authority: " + authorityName);
            roles.add(authorityName);
        }

        if(roles.contains(RolesEnum.Admin.toString())){
            return cardDAO.selectCardById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "card with id [%s] NOT found"
                                    .formatted(id)));
        }else{
            return cardDAO.selectCardByIdCreatedBy(id, createdBy)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "card with id [%s] created by [%s] NOT found"
                                    .formatted(id,createdBy)));
        }

    }

    public List<Card> getUserCardByCriteria(String searchString, String sortBy, String token){
        List<String> roles = new ArrayList<>();
        String createdBy = jwtUtil.getSubject(token.substring(7));

        Collection<GrantedAuthority> authorities = jwtUtil.extractAuthorities(token.substring(7));

        // Iterate over the collection of GrantedAuthority objects
        for (GrantedAuthority authority : authorities) {
            // Access individual authority/role
            String authorityName = authority.getAuthority();
            System.out.println("Authority: " + authorityName);
            roles.add(authorityName);
        }

        // Define the list of sortable fields
        List<String> SORTABLE_FIELDS = Arrays.asList("name", "color","status", "createdAt");

        if (sortBy != null && !SORTABLE_FIELDS.contains(sortBy)) {
            throw new InvalidSortFieldException("Invalid sortBy provided. Allowed values are: " + SORTABLE_FIELDS + ". Date format is 2024-04-20");
        }

        Sort sort = Sort.by(sortBy).ascending();

        //get role from token...if member return only own else return all
        if(roles.contains(RolesEnum.Member.toString())){
            List<Card> cardsList = cardDAO.selectCardByCriteriaAndCreatedBy(searchString, createdBy, sort);
            if(cardsList.isEmpty()){
                throw new ResourceNotFoundException(
                        "card with search value [%s] NOT found"
                                .formatted(searchString));
            }else{
                return cardsList;
            }


        }else if(roles.contains(RolesEnum.Admin.toString())){
            List<Card> cardsList = cardDAO.selectCardByCriteria(searchString, sort);
            if(cardsList.isEmpty()){
                throw new ResourceNotFoundException(
                        "card with search value [%s] NOT found"
                                .formatted(searchString));
            }else{
                return cardsList;
            }

        }

        throw new RequestValidationException("Request is not valid");

    }

    public ResponseEntity<?> addCard(CardRegistrationRequest cardRegistrationRequest, String token){

        String username = jwtUtil.getSubject(token.substring(7));
        //validate color format
        if(!cardRegistrationRequest.color().matches("#[A-Za-z0-9]{6}")) {
            throw new InvalidColorFormatException("Color not formatted correctly. Correct format is 6 alphanumeric characters prefixed with a # e.g #ghd5g4");
        }

        if(cardRegistrationRequest.name() == null || cardRegistrationRequest.name().isEmpty()){
            throw new RequiredFieldException("Name is required");
        }

        //add
        Card card = new Card(
                cardRegistrationRequest.name(),
                cardRegistrationRequest.description(),
                cardRegistrationRequest.color(),
                CardStatusEnum.TODO.getName(),
                Date.from(Instant.now()),
                username);
        cardDAO.insertCard(card);

        CardGeneralResponse cardGeneralResponse = new CardGeneralResponse(
                HttpStatus.OK.value(),
                "Card saved successfully"
        );

        return ResponseEntity.ok()
                .body(cardGeneralResponse);
    }

    public ResponseEntity<?> deleteCardById(Integer id, String token){
        List<String> roles = new ArrayList<>();
        String createdBy = jwtUtil.getSubject(token.substring(7));

        Collection<GrantedAuthority> authorities = jwtUtil.extractAuthorities(token.substring(7));

        // Iterate over the collection of GrantedAuthority objects
        for (GrantedAuthority authority : authorities) {
            // Access individual authority/role
            String authorityName = authority.getAuthority();
            System.out.println("Authority: " + authorityName);
            roles.add(authorityName);
        }

        if(roles.contains(RolesEnum.Member.toString())){
            //check if card owned by user exits
            if(!cardDAO.cardWithIdCreatedByExists(id, createdBy)){
                throw new ResourceNotFoundException("Card with id [%s] owned by [%s] not found " .formatted(id,createdBy));
            }

        }else if (roles.contains(RolesEnum.Admin.toString())){
            if(!cardDAO.cardWithIdExists(id)){
                throw new ResourceNotFoundException("Card with id [%s] not found " .formatted(id));
            }
        }

        cardDAO.deleteCardById(id);

        CardGeneralResponse cardGeneralResponse = new CardGeneralResponse(
                HttpStatus.OK.value(),
                String.format("Card with id [%s] deleted successfully",id)
        );

        return ResponseEntity.ok()
                .body(cardGeneralResponse);
    }

    public ResponseEntity<?> updateCard(Integer cardId, CardUpdateRequest cardUpdateRequest, String token){
        List<String> roles = new ArrayList<>();
        String createdBy = jwtUtil.getSubject(token.substring(7));

        // Define the list of status fields
        List<String> STATUS_FIELDS = Arrays.asList("To Do", "In Progress","Done");

        if(cardUpdateRequest.name() == null || cardUpdateRequest.name().isEmpty()){
            throw new RequiredFieldException("Name is required");
        }

        if (cardUpdateRequest.status() != null && !STATUS_FIELDS.contains(cardUpdateRequest.status())) {
            throw new InvalidStatusFieldException("Invalid status provided. Allowed values are: " + STATUS_FIELDS);
        }

        Collection<GrantedAuthority> authorities = jwtUtil.extractAuthorities(token.substring(7));

        // Iterate over the collection of GrantedAuthority objects
        for (GrantedAuthority authority : authorities) {
            // Access individual authority/role
            String authorityName = authority.getAuthority();
            System.out.println("Authority: " + authorityName);
            roles.add(authorityName);
        }
        Card card = new Card();
        if(roles.contains(RolesEnum.Member.toString())){
            //check if card owned by user exits
            card = cardDAO.selectCardByIdCreatedBy(cardId, createdBy)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "card with id [%s] and created by [%s] NOT found"
                                    .formatted(cardId, createdBy)));

        }else if (roles.contains(RolesEnum.Admin.toString())){
            card = cardDAO.selectCardById(cardId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "card with id [%s] NOT found"
                                    .formatted(cardId)));
        }

        boolean changes = false;

        if(!cardUpdateRequest.name()
                .equals(card.getName()) ){
            card.setName(cardUpdateRequest.name());
            changes = true;
        }

        if(!cardUpdateRequest.description()
                .equals(card.getDescription()) ){
            card.setDescription(cardUpdateRequest.description());
            changes = true;
        }

        if(!cardUpdateRequest.color()
                .equals(card.getColor()) ){
            card.setColor(cardUpdateRequest.color());
            changes = true;
        }

        if(cardUpdateRequest.status() != null && !cardUpdateRequest.status()
                .equals(card.getStatus()) ){
            card.setStatus(cardUpdateRequest.status());
            changes = true;
        }

        if(!changes){
            throw new RequestValidationException("no data changes found");
        }

        cardDAO.updateCard(card);

        CardGeneralResponse cardGeneralResponse = new CardGeneralResponse(
                HttpStatus.OK.value(),
                String.format("Card with id [%s] updated successfully",cardId)
        );

        return ResponseEntity.ok()
                .body(cardGeneralResponse);

    }



}
