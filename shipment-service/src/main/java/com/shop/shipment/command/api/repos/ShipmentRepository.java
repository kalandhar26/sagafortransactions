package com.shop.shipment.command.api.repos;

import com.shop.shipment.command.api.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment,String> {
}
