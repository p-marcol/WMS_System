package com.inz.apimodels.user.upsert_details;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.util.Date;

@Getter
public class UpsertDetailsRequest {
    private Long userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date dateOfBirth;
}
