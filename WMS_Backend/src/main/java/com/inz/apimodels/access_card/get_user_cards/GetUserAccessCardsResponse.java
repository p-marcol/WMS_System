package com.inz.apimodels.access_card.get_user_cards;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class GetUserAccessCardsResponse {
    public Long id;
    public String cardUid;
    public String type;
    public String description;
}
