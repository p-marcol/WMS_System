package com.inz.apimodels.user.get_all_users;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class GetAllUsersResponseModel {
    public Long id;
    public String username;
    public String firstName;
    public String lastName;
    public String email;
    public String authority;
    public boolean isArchived;
    public boolean isArchivable;
    public boolean isEditable;
    public boolean isDeletable;
}
