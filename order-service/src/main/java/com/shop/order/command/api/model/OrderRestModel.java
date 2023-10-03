package com.shop.order.command.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRestModel implements Serializable {

    private String productId;
    private String userId;
    private String addressId;
    private Integer quantity;
}
