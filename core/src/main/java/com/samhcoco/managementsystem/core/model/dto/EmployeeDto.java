package com.samhcoco.managementsystem.core.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {
    private long id;
    private String authId;
    private String firstName;
    private String middleNames;
    private String lastName;
    private long departmentId;
    private String email;
    private String phone;
    private String addressFirstLine;
    private String addressSecondLine;
    private String addressCity;
    private String addressPostCode;
}