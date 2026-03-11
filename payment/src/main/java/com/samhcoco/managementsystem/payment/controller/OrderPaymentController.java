package com.samhcoco.managementsystem.payment.controller;

import com.samhcoco.managementsystem.core.exception.InvalidInputApiException;
import com.samhcoco.managementsystem.core.model.OrderPayment;
import com.samhcoco.managementsystem.core.model.User;
import com.samhcoco.managementsystem.core.model.dto.ProductOrderDtoList;
import com.samhcoco.managementsystem.core.service.JwtAuthService;
import com.samhcoco.managementsystem.core.service.impl.ProductOrderDtoListValidator;
import com.samhcoco.managementsystem.core.utils.ApiVersion;
import com.samhcoco.managementsystem.payment.service.OrderPaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.samhcoco.managementsystem.core.service.JwtAuthService.USER_ID;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderPaymentController {

    private final OrderPaymentService orderPaymentService;
    private final JwtAuthService jwtAuthService;
    private final ProductOrderDtoListValidator productOrdersDtoValidator;

    @PreAuthorize("hasRole('user')")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(ApiVersion.VERSION_1 + "/users/{userId}/orders/payment")
    public ResponseEntity<Object> orderPayment(@RequestBody OrderPayment orderPayment,
                                               @PathVariable Long userId) {
        try {
            final long internalUserId = jwtAuthService.verifyPrincipalAuthorisedAndReturnId(USER_ID, User.class);
            orderPayment.setUserId(userId);

            final Map<String, String> failureReasons = productOrdersDtoValidator.validateCreate(new ProductOrderDtoList(orderPayment.getOrders()));
            if (!failureReasons.isEmpty()) {
                log.error("Failed to create Order Payment for {}: {}", orderPayment, failureReasons);
                throw new InvalidInputApiException(BAD_REQUEST.name(), failureReasons);
            }

            final OrderPayment orderSuccess = orderPaymentService.debitOrderPayment(orderPayment);
            if (orderSuccess != null) {
                return ResponseEntity.status(HttpStatus.OK).body(orderSuccess);
            }
        } catch (Exception e) {
            log.error("Failed to take payment for {} : {}", orderPayment, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
