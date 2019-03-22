package com.leo.order_service.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Preconditions;
import com.leo.order_service.model.ProductOrder;
import com.leo.order_service.service.OrderService;
import com.leo.order_service.service.ProductClient;
import com.leo.order_service.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductClient productClient;

    @Override
    public ProductOrder save(Integer userId, Integer productId) {
        String productJson = productClient.findById(productId);
        JsonNode jsonNode = JsonUtils.str2JsonNode(productJson);
        Preconditions.checkNotNull(jsonNode);
        ProductOrder productOrder = new ProductOrder();
        productOrder.setCreateTime(LocalDateTime.now());
        productOrder.setUserId(userId);
        productOrder.setTradeNo(UUID.randomUUID().toString());
        productOrder.setProductName(jsonNode.get("name").toString());
        productOrder.setPrice(jsonNode.get("price").toString());
        return productOrder;
    }
}
