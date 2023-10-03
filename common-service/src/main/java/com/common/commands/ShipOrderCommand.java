package com.common.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.io.Serializable;

@Data
@Builder
public class ShipOrderCommand implements Serializable {

    @TargetAggregateIdentifier
    private String shipmentId;
    private String orderId;
}
