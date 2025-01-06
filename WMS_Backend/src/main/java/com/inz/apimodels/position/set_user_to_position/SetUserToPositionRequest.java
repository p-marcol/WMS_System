package com.inz.apimodels.position.set_user_to_position;

import lombok.Getter;

import java.util.List;

@Getter
public class SetUserToPositionRequest {
    public Long unitId;
    public List<SetUserToPositionRequestListItem> positionList;
}
