package com.inz.apimodels.user_access.get_user_accesses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserAccessesResponseItem {
    public Long id;
    public String deviceSymbol;
    public String accessCardUid;
    public LocalDateTime at;
}
