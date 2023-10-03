package com.shop.shipment.command.api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "shipments")
public class Shipment implements Serializable {

    @Id
    private String shipmentId;

    private String orderId;

    private String shipmentStatus;
}
