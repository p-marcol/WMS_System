package com.inz.apimodels.user.get_user_short;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class GetUserShortResponse {
    public Long id;
    public String name;
    public char userType;
}
