package com.logicea.card;


import com.logicea.jwt.JWTUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cards")
public class CardController {

    private final CardService cardService;
    private final JWTUtil jwtUtil;

    public CardController(CardService cardService, JWTUtil jwtUtil) {
        this.cardService = cardService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> registerCard(
            @RequestBody CardRegistrationRequest cardRegistrationRequest,
            @RequestHeader("Authorization") String token){
        return cardService.addCard(cardRegistrationRequest, token);
    }

    @GetMapping
    public List<Card> getCards(@RequestHeader("Authorization") String token){
        return cardService.getAllCards(token);
    }

    @GetMapping(path = "{cardId}")
    public Card getCardsById(
            @PathVariable("cardId") Integer cardId,
            @RequestHeader("Authorization") String token){
        return cardService.getCardByIdCreatedBy(cardId, token);
    }

    @GetMapping("{searchString}/{sortBy}")
    public List<Card> searchCards(@PathVariable("searchString") String searchString,
                                         @PathVariable("sortBy") String sortBy,
                                            @RequestHeader("Authorization") String token){
        return cardService.getUserCardByCriteria(searchString, sortBy, token);
    }

    @DeleteMapping("{cardId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("cardId") Integer cardId,
                               @RequestHeader("Authorization") String token){
         return cardService.deleteCardById(cardId, token);
    }

    @PutMapping(path = "{cardId}")
    public ResponseEntity<?> updateCustomer(
            @PathVariable("cardId") Integer cardId,
            @RequestBody CardUpdateRequest cardUpdateRequest,
            @RequestHeader("Authorization") String token){
         return cardService.updateCard(cardId,cardUpdateRequest, token);
    }

}
