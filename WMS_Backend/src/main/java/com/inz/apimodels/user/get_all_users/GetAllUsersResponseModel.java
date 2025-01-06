package com.inz.apimodels.user.get_all_users;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetAllUsersResponseModel {
    public Long id;
    public String username;
    public String firstName;
    public String lastName;
    public String shortName;
    public String email;
    public String authority;
    public String position;
    public String unit;
    public boolean isArchived;
    public boolean isArchivable;
    public boolean isEditable;
    public boolean isDeletable;
}
