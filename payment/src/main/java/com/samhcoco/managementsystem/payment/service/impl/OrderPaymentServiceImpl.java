package com.samhcoco.managementsystem.payment.service.impl;

import com.samhcoco.managementsystem.core.model.OrderPayment;
import com.samhcoco.managementsystem.payment.service.OrderPaymentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderPaymentServiceImpl implements OrderPaymentService {

    private static final String ORDER_PAYMENTS_TOPIC = "order-payments";

    private final KafkaTemplate<String, OrderPayment> kafkaTemplate;

    @Override
    public OrderPayment debitOrderPayment(@NonNull OrderPayment orderPayment) {
        try {
            // Call to payment API here - following code here only executes if the payment is successful
            orderPayment.setOrderId(UUID.randomUUID());
            kafkaTemplate.send(ORDER_PAYMENTS_TOPIC, orderPayment);
            return orderPayment;
        } catch (Exception e) {
            log.error("Order Payment failed - failed to send '{}' to Kafka TOPIC '{}': {}", orderPayment, ORDER_PAYMENTS_TOPIC, e.getMessage());
        }
        return null;
    }
}
