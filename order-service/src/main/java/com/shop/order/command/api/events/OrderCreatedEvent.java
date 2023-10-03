package com.shop.order.command.api.events;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderCreatedEvent implements Serializable {

    private String orderId;
    private String productId;
    private String userId;
    private String addressId;
    private Integer quantity;
    private String orderStatus;
}
