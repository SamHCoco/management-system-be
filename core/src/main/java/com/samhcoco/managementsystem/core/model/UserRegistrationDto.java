package com.samhcoco.managementsystem.core.model;

import com.samhcoco.managementsystem.core.service.AuthIdentifiable;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRegistrationDto implements AuthIdentifiable {
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
