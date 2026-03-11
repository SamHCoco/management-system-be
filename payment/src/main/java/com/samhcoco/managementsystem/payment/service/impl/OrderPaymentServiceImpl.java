package com.samhcoco.managementsystem.payment.service.impl;

import com.samhcoco.managementsystem.core.model.OrderPayment;
import com.samhcoco.managementsystem.payment.service.OrderPaymentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderPaymentServiceImpl implements OrderPaymentService {

    private static final String ORDER_PAYMENTS_TOPIC = "order-payments";

    private final KafkaTemplate<String, OrderPayment> kafkaTemplate;

    @Override
    public boolean debitOrderPayment(@NonNull OrderPayment orderPayment) {
        orderPayment.setOrderId(UUID.randomUUID());
        kafkaTemplate.send(ORDER_PAYMENTS_TOPIC, orderPayment);
        return true;
    }
}
