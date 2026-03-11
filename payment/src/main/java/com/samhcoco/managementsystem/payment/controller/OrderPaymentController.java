package com.samhcoco.managementsystem.payment.controller;

import com.samhcoco.managementsystem.core.utils.ApiVersion;
import com.samhcoco.managementsystem.core.model.OrderPayment;
import com.samhcoco.managementsystem.payment.service.OrderPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderPaymentController {

    private final OrderPaymentService orderPaymentService;

    @PostMapping(ApiVersion.VERSION_1 + "/order-payment")
    public ResponseEntity<Object> orderPayment(@RequestBody OrderPayment orderPayment) {
        final boolean isSuccess = orderPaymentService.debitOrderPayment(orderPayment);
        if (isSuccess) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
