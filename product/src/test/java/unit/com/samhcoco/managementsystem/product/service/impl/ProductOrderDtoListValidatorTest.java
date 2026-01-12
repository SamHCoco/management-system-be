package unit.com.samhcoco.managementsystem.product.service.impl;

import com.samhcoco.managementsystem.product.model.dto.ProductOrderDto;
import com.samhcoco.managementsystem.product.model.dto.ProductOrderDtoList;
import com.samhcoco.managementsystem.product.repository.ProductRepository;
import com.samhcoco.managementsystem.product.service.impl.ProductOrderDtoListValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductOrderDtoListValidatorTest {

    private ProductOrderDtoListValidator underTest;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        underTest = new ProductOrderDtoListValidator(productRepository);
    }

    @Test
    public void testValidateCreate_happyPath() {
        when(productRepository.existsByIdInAndDeletedFalse(anySet())).thenReturn(true);

        final List<ProductOrderDto> validOrders = List.of(
                new ProductOrderDto(0, 1, (short) 5),
                new ProductOrderDto(0, 2, (short) 1)
        );

        final ProductOrderDtoList validProductOrderDtoList = new ProductOrderDtoList(validOrders);

        final Map<String, String> failureReasons = underTest.validateCreate(validProductOrderDtoList);

        assertThat(failureReasons).isEmpty();
        verify(productRepository).existsByIdInAndDeletedFalse(anySet());
    }

}
