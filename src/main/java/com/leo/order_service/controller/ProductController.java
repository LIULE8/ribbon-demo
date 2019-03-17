package com.leo.order_service.controller;

import com.leo.order_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("save")
    public Object save(@RequestParam("user_id") Integer userId,
                       @RequestParam("product_id") Integer productId) {
        return productService.save(userId, productId);
    }
}
