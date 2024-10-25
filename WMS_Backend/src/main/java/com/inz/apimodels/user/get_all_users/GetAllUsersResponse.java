package com.inz.apimodels.user.get_all_users;

import com.inz.WMS_Backend.entity.dictionaries.Authority;
import lombok.Builder;
import lombok.Setter;

import java.util.List;

@Setter
@Builder
public class GetAllUsersResponse {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public List<Authority> authorities;
    public boolean isArchived;
}
