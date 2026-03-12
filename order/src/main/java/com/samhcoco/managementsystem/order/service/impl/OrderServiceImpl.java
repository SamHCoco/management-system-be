package com.samhcoco.managementsystem.order.service.impl;

import com.samhcoco.managementsystem.core.model.OrderPayment;
import com.samhcoco.managementsystem.core.model.ProductOrder;
import com.samhcoco.managementsystem.core.repository.ProductOrderRepository;
import com.samhcoco.managementsystem.order.model.CompletedOrderPayment;
import com.samhcoco.managementsystem.order.model.Order;
import com.samhcoco.managementsystem.order.repository.OrderRepository;
import com.samhcoco.managementsystem.order.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductOrderRepository productOrderRepository;

    @Override
    @Transactional
    public CompletedOrderPayment processOrderPayment(@NonNull OrderPayment orderPayment) {
        try {
            final Order orderDetails = orderRepository.save(Order.builder()
                                                                .id(orderPayment.getOrderId())
                                                                .userId(orderPayment.getUserId())
                                                                .build());

            final List<ProductOrder> productOrders = productOrderRepository.saveAll(orderPayment.getOrders()
                                                                            .stream()
                                                                            .map(com.samhcoco.managementsystem.core.model.dto.ProductOrderDto::toProductOrder)
                                                                            .toList());

            return CompletedOrderPayment.builder()
                                        .orderDetails(orderDetails)
                                        .orders(productOrders)
                                        .build();
        } catch (Exception e) {
            log.error("Failed process {}: {}", orderPayment, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "order-payments", groupId = "order-payments-group")
    public void consumeOrderPayment(OrderPayment orderPayment) {
        log.info("Received 'order-payment' record: '{}'", orderPayment);
        log.info("Attempting to process Order Payment ..."); // todo - remove this

        final CompletedOrderPayment completedOrder = processOrderPayment(orderPayment);
        log.info("Successfully {}", completedOrder);
    }

}
