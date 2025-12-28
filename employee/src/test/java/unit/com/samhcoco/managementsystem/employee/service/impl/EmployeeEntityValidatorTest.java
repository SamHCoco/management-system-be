package unit.com.samhcoco.managementsystem.employee.service.impl;

import com.samhcoco.managementsystem.core.model.Employee;
import com.samhcoco.managementsystem.core.model.errorMessages;
import com.samhcoco.managementsystem.employee.repository.EmployeeRepository;
import com.samhcoco.managementsystem.employee.service.impl.EmployeeEntityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static com.samhcoco.managementsystem.employee.service.impl.EmployeeEntityValidator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeEntityValidatorTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private errorMessages errorMessages;

    private EmployeeEntityValidator underTest;

    @BeforeEach
    public void setup() {
        underTest = new EmployeeEntityValidator(employeeRepository, errorMessages);
    }

    @Test
    public void testValidateCreate_happyPath() {
        when(employeeRepository.existsByEmail(any())).thenReturn(false);
        when(employeeRepository.existsByPhone(any())).thenReturn(false);

        Employee validEmployee = buildValidEmployee();
        validEmployee.setId(0);

        Map<String, String> errors = underTest.validateCreate(validEmployee);

        assertThat(errors).isEmpty();

        verify(employeeRepository).existsByEmail(any());
        verify(employeeRepository).existsByPhone(any());
    }

    @Test
    public void testValidateCreate_fail_entityNull() {
        Map<String, String> errors = underTest.validateCreate(null);
        assertThat(errors).containsKey(ENTITY);
        assertThat(errors.size()).isEqualTo(1);
    }

    @Test
    public void testValidateCreate_fail_AllFieldsInvalid() {
        Employee invalidEmployee = Employee.builder()
                                           .id(1)
                                           .firstName(" ")
                                           .email("")
                                           .departmentId(0)
                                           .addressFirstLine("")
                                           .build();

        Map<String, String> errors = underTest.validateCreate(invalidEmployee);

        assertAll(
                () -> assertThat(errors).containsKey(FIRST_NAME),
                () -> assertThat(errors).containsKey(LAST_NAME),
                () -> assertThat(errors).containsKey(EMAIL),
                () -> assertThat(errors).containsKey(PHONE),
                () -> assertThat(errors).containsKey(EMPLOYEE_DEPARTMENT_ID),
                () -> assertThat(errors).containsKey(ADDRESS_FIRST_LINE),
                () -> assertThat(errors).containsKey(ADDRESS_CITY),
                () -> assertThat(errors).containsKey(ADDRESS_POST_CODE),
                () -> assertThat(errors).containsKey(ID)
        );
    }

    @Test
    public void testValidateCreate_fail_emailAlreadyExists() {
        Employee invalid = buildValidEmployee();
        invalid.setId(0);

        when(employeeRepository.existsByEmail(any())).thenReturn(true);
        when(employeeRepository.existsByPhone(any())).thenReturn(false);

        Map<String, String> errors = underTest.validateCreate(invalid);

        assertThat(errors).containsKey(EMAIL);
        assertThat(errors.size()).isEqualTo(1);

        verify(employeeRepository).existsByEmail(any());
        verify(employeeRepository).existsByPhone(any());
    }

    @Test
    public void testValidateCreate_fail_phoneAlreadyExists() {
        Employee invalid = buildValidEmployee();
        invalid.setId(0);

        when(employeeRepository.existsByPhone(any())).thenReturn(true);
        when(employeeRepository.existsByEmail(any())).thenReturn(false);

        Map<String, String> errors = underTest.validateCreate(invalid);

        assertThat(errors).containsKey(PHONE);
        assertThat(errors.size()).isEqualTo(1);

        verify(employeeRepository).existsByEmail(any());
        verify(employeeRepository).existsByPhone(any());
    }

    @Test
    public void testValidateUpdate_happyPath() {
        Employee valid = buildValidEmployee();

        when(employeeRepository.findByEmail(any())).thenReturn(null);
        when(employeeRepository.findByPhone(any())).thenReturn(null);

        Map<String, String> errors = underTest.validateUpdate(valid);

        assertThat(errors).isEmpty();

        verify(employeeRepository).findByEmail(any());
        verify(employeeRepository).findByPhone(any());
    }

    @Test
    public void testValidateUpdate_fail_entityNull() {
        Map<String, String> errors = underTest.validateUpdate(null);
        assertThat(errors).containsKey(ENTITY);
        assertThat(errors.size()).isEqualTo(1);
    }

    @Test
    public void testValidateUpdate_fail_allFieldsInvalid() {
        Employee invalid = Employee.builder()
                                   .id(0)
                                   .departmentId(0)
                                   .firstName("")
                                   .email(" ")
                                   .phone(" ")
                                   .build();

        Map<String, String> errors = underTest.validateUpdate(invalid);

        assertAll(
                () -> assertThat(errors).containsKey(ID),
                () -> assertThat(errors).containsKey(FIRST_NAME),
                () -> assertThat(errors).containsKey(LAST_NAME),
                () -> assertThat(errors).containsKey(EMAIL),
                () -> assertThat(errors).containsKey(PHONE),
                () -> assertThat(errors).containsKey(EMPLOYEE_DEPARTMENT_ID),
                () -> assertThat(errors).containsKey(ADDRESS_FIRST_LINE),
                () -> assertThat(errors).containsKey(ADDRESS_CITY),
                () -> assertThat(errors).containsKey(ADDRESS_POST_CODE)
        );
    }

    @Test
    public void testValidateUpdate_fail_emailAlreadyExists() {
        Employee employee = buildValidEmployee();

        when(employeeRepository.findByEmail(any())).thenReturn(new Employee());
        when(employeeRepository.findByPhone(any())).thenReturn(null);

        Map<String, String> errors = underTest.validateUpdate(employee);

        assertThat(errors).containsKey(EMAIL);
        assertThat(errors.size()).isEqualTo(1);

        verify(employeeRepository).findByPhone(any());
        verify(employeeRepository).findByEmail(any());
    }

    @Test
    public void testValidateUpdate_fail_phoneAlreadyExists() {
        Employee employee = buildValidEmployee();

        when(employeeRepository.findByEmail(any())).thenReturn(null);
        when(employeeRepository.findByPhone(any())).thenReturn(new Employee());

        Map<String, String> errors = underTest.validateUpdate(employee);

        assertThat(errors).containsKey(PHONE);
        assertThat(errors.size()).isEqualTo(1);

        verify(employeeRepository).findByPhone(any());
        verify(employeeRepository).findByEmail(any());
    }

    private Employee buildValidEmployee() {
        return Employee.builder()
                .id(1)
                .firstName("John")
                .middleNames("H")
                .lastName("Smith")
                .email("john.h.smith@yopmail.com")
                .phone("+44-20-7946-0958")
                .departmentId(1L)
                .addressFirstLine("123 Main St")
                .addressCity("London")
                .addressPostCode("W1D 1NU")
                .build();
    }

}
