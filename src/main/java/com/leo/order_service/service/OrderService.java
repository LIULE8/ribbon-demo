package com.leo.order_service.service;

import com.leo.order_service.model.ProductOrder;

public interface OrderService {

    ProductOrder save(Integer userId, Integer productId);
}
