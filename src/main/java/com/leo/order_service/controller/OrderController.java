package com.leo.order_service.controller;

import com.google.common.collect.Maps;
import com.leo.order_service.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("save")
    @HystrixCommand(fallbackMethod = "saveFailCallBack")
    public Object save(@RequestParam("user_id") Integer userId,
                       @RequestParam("product_id") Integer productId,
                       HttpServletRequest request) {
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
    public Object saveFailCallBack(Integer userId, Integer productId,HttpServletRequest request) {
        //监控报警
        String saveOrderKey = "save-order";
        String sendValue = redisTemplate.opsForValue().get(saveOrderKey);
        final String ip = request.getRemoteAddr();
        new Thread(()->{
            if (StringUtils.isBlank(sendValue)) {
                System.out.println("紧急短信，用户下单失败，请立刻查找原因，ip=" + ip);
                //发送一个http请求，请用短信服务，todo
                redisTemplate.opsForValue().set(saveOrderKey, "save-order-fail", 20, TimeUnit.MINUTES);
            } else {
                System.out.println("已经发送过短信，20s内不重复发送");
            }
        }).start();
        Map<String, Object> msg = Maps.newHashMap();
        msg.put("code", -1);
        msg.put("msg", "抢购人数太多，请稍后重试");
        return msg;
    }
}
