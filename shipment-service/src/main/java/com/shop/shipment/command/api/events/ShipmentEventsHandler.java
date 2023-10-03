package com.shop.shipment.command.api.events;

import com.common.events.OrderShippedEvent;
import com.shop.shipment.command.api.entities.Shipment;
import com.shop.shipment.command.api.repos.ShipmentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShipmentEventsHandler {

    private ShipmentRepository shipmentRepository;

    @Autowired
    public ShipmentEventsHandler(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @EventHandler
    public void on(OrderShippedEvent event) {
        Shipment shipment = new Shipment();
        BeanUtils.copyProperties(event,shipment);
        shipmentRepository.save(shipment);
    }
}
