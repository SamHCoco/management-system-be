package com.samhcoco.managementsystem.order.service;

import com.samhcoco.managementsystem.core.model.OrderPayment;
import com.samhcoco.managementsystem.order.model.CompletedOrderPayment;

public interface OrderService {
    CompletedOrderPayment processOrderPayment(OrderPayment orderPayment);
}
