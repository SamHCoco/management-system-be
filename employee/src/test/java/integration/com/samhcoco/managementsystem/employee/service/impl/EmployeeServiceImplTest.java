package integration.com.samhcoco.managementsystem.employee.service.impl;

import com.samhcoco.managementsystem.employee.EmployeeApplication;
import com.samhcoco.managementsystem.employee.service.EmployeeService;
import integration.com.samhcoco.managementsystem.employee.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest(classes = {EmployeeApplication.class, TestSecurityConfig.class})
public class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService underTest;

    @Test
    public void testListAllEmployees_nullArgument_NullPointerException() {
        assertThrows(NullPointerException.class, () ->
                underTest.listAllEmployees(null)
        );
    }

    @Test
    public void testCreate_nullArgument_NullPointerException() {
        assertThrows(NullPointerException.class, () ->
                underTest.create(null)
        );
    }

    @Test
    public void testUpdate_nullArgument_NullPointerException() {
        assertThrows(NullPointerException.class, () ->
                underTest.create(null)
        );
    }

}
