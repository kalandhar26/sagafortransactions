package com.common.events;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OrderCompletedEvent implements Serializable {

    private String orderId;

    private String orderStatus;
}
