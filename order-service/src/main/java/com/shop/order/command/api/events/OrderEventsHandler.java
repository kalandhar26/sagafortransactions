package com.shop.order.command.api.events;

import com.common.events.OrderCancelledEvent;
import com.common.events.OrderCompletedEvent;
import com.shop.order.command.api.entities.Order;
import com.shop.order.command.api.model.OrderRestModel;
import com.shop.order.command.api.repos.OrderRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderEventsHandler {

    private OrderRepository orderRepository;

    public OrderEventsHandler() {
    }

    @Autowired
    public OrderEventsHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @EventHandler
    public void on(OrderCreatedEvent event) {

        Order order = new Order();
        BeanUtils.copyProperties(event, order);
        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCompletedEvent event) {

        Optional<Order> order = orderRepository.findById(event.getOrderId());

        if(order.isPresent()) {
            Order currentOrder = order.get();
            currentOrder.setOrderStatus(event.getOrderStatus());
            orderRepository.save(currentOrder);
        }else{
            throw  new RuntimeException("Order Not Present");
        }
    }


    @EventHandler
    public  void  on(OrderCancelledEvent event){
        Optional<Order> order = orderRepository.findById(event.getOrderId());

        if(order.isPresent()) {
            Order currentOrder = order.get();
            currentOrder.setOrderStatus(event.getOrderStatus());
            orderRepository.save(currentOrder);
        }else{
            throw  new RuntimeException("Order Not Present");
        }
    }


}
