package com.inz.apimodels.auth.refresh;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefreshResponse {
    public String token;
    public String refreshToken;
}
