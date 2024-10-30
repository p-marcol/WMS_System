package com.inz.apimodels.user.change_authority;

import lombok.Getter;

@Getter
public class ChangeAuthorityRequest {
    private Long userId;
    private Long authorityId;
}
