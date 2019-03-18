package com.leo.order_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrder implements Serializable {
    private static final long serialVersionUID = 2803253748446959752L;
    private Integer id;
    private String productName;
    private String tradeNo;
    private String price;
    private LocalDateTime createTime;
    private Integer userId;
}
