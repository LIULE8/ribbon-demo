package com.leo.order_service.service.impl;

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

        Object forObject = restTemplate.getForObject("http://product-service/api/v1/product/find?id=" + productId, Object.class);
        System.out.println(forObject);
        ProductOrder productOrder = new ProductOrder();
        productOrder.setCreateTime(LocalDateTime.now());
        productOrder.setUserId(userId);
        productOrder.setTradeNo(UUID.randomUUID().toString());
        return productOrder;
    }
}
