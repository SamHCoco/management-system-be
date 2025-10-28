package integration.com.samhcoco.managementsystem.employee.service.impl;

import com.samhcoco.managementsystem.employee.EmployeeApplication;
import com.samhcoco.managementsystem.employee.service.EmployeeService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = EmployeeApplication.class)
public class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService underTest;

    @Test
    public void testListAllEmployees_nullArgument_constraintViolationException() {
        assertThrows(ConstraintViolationException.class, () ->
                underTest.listAllEmployees(null)
        );
    }

    @Test
    public void testCreate_nullArgument_constraintViolationException() {
        assertThrows(ConstraintViolationException.class, () ->
                underTest.create(null)
        );
    }

    @Test
    public void testUpdate_nullArgument_constraintViolationException() {
        assertThrows(ConstraintViolationException.class, () ->
                underTest.create(null)
        );
    }

}
