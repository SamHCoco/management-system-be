package com.samhcoco.managementsystem.payment.service;

import com.samhcoco.managementsystem.core.model.User;
import com.samhcoco.managementsystem.core.model.OrderPayment;

public interface OrderPaymentService {
    /**
     * Takes payment from {@link User} for order.
     * @param orderPayment {@link OrderPayment} for an order.
     * @return True if payment succeeded, false otherwise.
     */
    boolean debitOrderPayment(OrderPayment orderPayment);
}
