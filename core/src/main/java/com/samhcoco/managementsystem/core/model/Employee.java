package com.samhcoco.managementsystem.core.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employee")
public class Employee extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_names")
    private String middleNames;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "employee_department_id")
    private long employeeDepartmentId;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address_first_line")
    private String addressFirstLine;

    @Column(name = "address_second_line")
    private String addressSecondLine;

    @Column(name = "address_city")
    private String addressCity;

    @Column(name = "address_post_code")
    private String addressPostCode;
}
