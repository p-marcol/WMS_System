package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.entity.AccessCard;
import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.service.iAccessCardService;
import com.inz.WMS_Backend.service.iUserService;
import com.inz.apimodels.access_card.get_user_cards.GetUserAccessCardsResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
@AllArgsConstructor
public class CardController {

    private final iAccessCardService accessCardService;
    private final iUserService userService;

    @PutMapping("/assign/{userId}/{cardUid}")
    public ResponseEntity<?> assignCard(@PathVariable Long userId, @PathVariable String cardUid) {
        String uid = cardUid.toUpperCase().replaceAll("[^0-9ABCDEF]", "");
        try {
            User user = userService.findById(userId);
            AccessCard ac = accessCardService.findByCardUid(uid).orElse(null);
            if (ac != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Card already assigned");
            }
            accessCardService.assignCard(user, uid);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User or card not found");
        }
        return ResponseEntity.ok().body("Card assigned");
    }

    @DeleteMapping("/delete/{cardUid}")
    public ResponseEntity<?> deleteCard(@PathVariable String cardUid) {
        String uid = cardUid.toUpperCase().replaceAll("[^0-9ABCDEF]", "");
        try {
            AccessCard ac = accessCardService.findByCardUid(uid).orElse(null);
            if (ac == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found");
            }
            accessCardService.deleteCard(ac);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("An error occurred");
        }
        return ResponseEntity.ok().body("Card deleted");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserAccessCards(@PathVariable Long userId) {
        try {
            List<AccessCard> accessCards = accessCardService.getUserAccessCards(userId);
            List<GetUserAccessCardsResponse> response = accessCards.stream()
                    .map(ac -> new GetUserAccessCardsResponse(ac.getId(), ac.getCardUid(), ac.getType().getType(), ac.getDescription()))
                    .toList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
