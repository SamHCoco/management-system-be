package unit.com.samhcoco.managementsystem.employee.service.impl;

import com.samhcoco.managementsystem.core.model.AppPage;
import com.samhcoco.managementsystem.core.model.Employee;
import com.samhcoco.managementsystem.core.repository.EmployeeRepository;
import com.samhcoco.managementsystem.employee.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeServiceImpl underTest;

    @BeforeEach
    public void setup() {
        underTest = new EmployeeServiceImpl(employeeRepository);
    }

    @Test
    public void testFindById_happyPath() {
        when(employeeRepository.findById(anyLong())).thenReturn(new Employee());

        Employee result = underTest.findById((long) 1);

        assertThat(result).isNotNull();

        verify(employeeRepository).findById(anyLong());
    }

    @Test
    public void testListAllEmployees_happyPath() {
        AppPage customAppPage = new AppPage();

        Page<Employee> expectedPage = new PageImpl<>(List.of(
                Employee.builder().id(1L).firstName("John").lastName("Doe").build(),
                Employee.builder().id(2L).firstName("Jane").lastName("Smith").build()
        ));

        when(employeeRepository.findAll(any(PageRequest.class))).thenReturn(expectedPage);

        Page<Employee> result = underTest.listAllEmployees(customAppPage);

        assertThat(result).isNotNull();
        assertThat(result.getContent().size()).isEqualTo(2);

        verify(employeeRepository).findAll(any(PageRequest.class));
    }

    @Test
    public void testCreate_happyPath() {
        Employee employee = new Employee();
        when(employeeRepository.save(any())).thenReturn(employee);

        Employee persisted = underTest.create(employee);

        assertThat(persisted).isEqualTo(employee);
        verify(employeeRepository).save(any());
    }

    @Test
    public void testUpdate_happyPath() {
        Employee employee = new Employee();
        when(employeeRepository.save(any())).thenReturn(employee);

        Employee updated = underTest.update(employee);

        assertThat(updated).isEqualTo(employee);
        verify(employeeRepository).save(any());
    }



    @Test
    public void testListAll_nullPointerException() {
        assertThrows(NullPointerException.class, () -> underTest.listAllEmployees(null));
    }

    @Test
    public void testCreate_nullPointerException() {
        assertThrows(NullPointerException.class, () -> underTest.create(null));
    }

    @Test
    public void testUpdate_nullPointerException() {
        assertThrows(NullPointerException.class, () -> underTest.update(null));
    }


}
