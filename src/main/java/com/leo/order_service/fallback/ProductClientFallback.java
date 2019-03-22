package com.leo.order_service.fallback;

import com.leo.order_service.service.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductClientFallback implements ProductClient {
    @Override
    public String findById(int id) {
        log.info("productClient find by id fail", id);
        return null;
    }
}
