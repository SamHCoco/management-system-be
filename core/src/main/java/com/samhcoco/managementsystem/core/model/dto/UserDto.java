package com.samhcoco.managementsystem.core.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private long id;
    private String authId;
    private String firstName;
    private String middleNames;
    private String lastName;
    private String email;
}
