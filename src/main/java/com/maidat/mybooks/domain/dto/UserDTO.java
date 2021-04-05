package com.maidat.mybooks.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class UserDTO {

    private String firstName;

    private String lastName;

    private String displayName;

    private String description;
}
