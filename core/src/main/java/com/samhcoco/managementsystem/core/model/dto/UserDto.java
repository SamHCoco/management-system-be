package com.samhcoco.managementsystem.core.model.dto;

import com.samhcoco.managementsystem.core.model.User;
import lombok.*;

@Builder
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
    private String password;

    public void removePassword() {
        this.password = null;
    }

    public User toUser() {
        return User.builder()
                .id(id)
                .authId(authId)
                .firstName(firstName)
                .middleNames(middleNames)
                .lastName(lastName)
                .email(email)
                .build();
    }
}
