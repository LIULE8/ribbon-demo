package com.leo.order_service.controller;

import com.google.common.collect.Maps;
import com.leo.order_service.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("save")
    @HystrixCommand(fallbackMethod = "saveFailCallBack")
    public Object save(@RequestParam("user_id") Integer userId,
                       @RequestParam("product_id") Integer productId) {
        Map<String, Object> msg = Maps.newHashMap();
        msg.put("code", 0);
        msg.put("data", orderService.save(userId, productId));
        return msg;
    }

    /**
     * 方法签名需要和api方法一致
     *
     * @param userId
     * @param productId
     * @return
     */
    public Object saveFailCallBack(Integer userId, Integer productId) {
        Map<String, Object> msg = Maps.newHashMap();
        msg.put("code", -1);
        msg.put("msg", "抢购人数太多，请稍后重试");
        return msg;
    }
}
