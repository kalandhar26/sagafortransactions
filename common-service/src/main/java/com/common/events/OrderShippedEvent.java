package com.common.events;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OrderShippedEvent implements Serializable {

    private String shipmentId;
    private String orderId;
    private String shipmentStatus;
}
