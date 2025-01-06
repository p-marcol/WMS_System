package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.Position;
import com.inz.apimodels.position.set_user_to_position.SetUserToPositionRequest;
import com.inz.apimodels.position.set_user_to_position.SetUserToPositionRequestListItem;

import java.util.List;

public interface iPositionService {
    void dropUserFromPosition(Long id);

    List<Position> getAllPositions();

    public void setUsersToPosition(List<SetUserToPositionRequestListItem> request, Long unitId);
}
