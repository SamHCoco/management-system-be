package com.samhcoco.managementsystem.core.model;

import com.samhcoco.managementsystem.core.model.dto.UserDto;
import com.samhcoco.managementsystem.core.service.AuthIdentifiable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "user")
public class User extends Auditable implements AuthIdentifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "auth_id")
    private String authId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_names")
    private String middleNames;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    public UserDto toDto() {
        return UserDto.builder()
                    .id(id)
                    .authId(authId)
                    .firstName(firstName)
                    .middleNames(middleNames)
                    .lastName(lastName)
                    .email(email)
                    .build();
    }
}
