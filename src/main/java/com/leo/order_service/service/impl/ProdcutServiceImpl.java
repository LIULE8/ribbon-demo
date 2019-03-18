package com.leo.order_service.service.impl;

import com.google.common.base.Preconditions;
import com.leo.order_service.model.ProductOrder;
import com.leo.order_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ProdcutServiceImpl implements ProductService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ProductOrder save(Integer userId, Integer productId) {
        ProductOrder productOrder = restTemplate.getForObject("http://product-service/api/v1/product/find?id=" + productId, ProductOrder.class);
        Preconditions.checkNotNull(productOrder);
        productOrder.setCreateTime(LocalDateTime.now());
        productOrder.setUserId(userId);
        productOrder.setTradeNo(UUID.randomUUID().toString());
        return productOrder;
    }
}
