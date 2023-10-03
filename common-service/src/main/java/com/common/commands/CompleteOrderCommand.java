package com.common.commands;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CompleteOrderCommand implements Serializable {

    private String orderId;
    private String OrderStatus;
}
