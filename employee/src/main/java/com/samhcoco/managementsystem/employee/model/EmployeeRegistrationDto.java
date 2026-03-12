package com.samhcoco.managementsystem.employee.model;

import com.samhcoco.managementsystem.core.model.Employee;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeRegistrationDto {
    private long id;
    private String authId;

    private String firstName;
    private String middleNames;
    private String lastName;

    private String password;

    private long departmentId;

    private String email;
    private String phone;

    private String addressFirstLine;
    private String addressSecondLine;
    private String addressCity;
    private String addressPostCode;

    private boolean isAdmin;

    public void removePassword() {
        this.password = null;
    }

    public Employee toEmployee() {
        return Employee.builder()
                .id(id)
                .authId(authId)
                .firstName(firstName)
                .middleNames(middleNames)
                .lastName(lastName)
                .departmentId(departmentId)
                .email(email)
                .phone(phone)
                .addressFirstLine(addressFirstLine)
                .addressSecondLine(addressSecondLine)
                .addressCity(addressCity)
                .addressPostCode(addressPostCode)
                .build();
    }

}
