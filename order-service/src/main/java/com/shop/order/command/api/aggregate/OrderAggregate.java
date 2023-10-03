package com.shop.order.command.api.aggregate;

import com.common.commands.CancelOrderCommand;
import com.common.commands.CompleteOrderCommand;
import com.common.events.OrderCancelledEvent;
import com.common.events.OrderCompletedEvent;
import com.shop.order.command.api.command.CreateOrderCommand;
import com.shop.order.command.api.events.OrderCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@AllArgsConstructor
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private String productId;
    private String userId;
    private String addressId;
    private Integer quantity;
    private String orderStatus;

    public OrderAggregate() {
    }


    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        // We handle command here and Validate the command
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);

        AggregateLifecycle.apply(orderCreatedEvent);

    }



    @EventSourcingHandler
    public void on (OrderCreatedEvent orderCreatedEvent){
       this.orderStatus= orderCreatedEvent.getOrderStatus();
       this.addressId=orderCreatedEvent.getAddressId();
       this.orderId=orderCreatedEvent.getOrderId();
       this.productId=orderCreatedEvent.getProductId();
       this.userId=orderCreatedEvent.getUserId();
       this.quantity=orderCreatedEvent.getQuantity();
    }

    @CommandHandler
    public void handle(CompleteOrderCommand completeOrderCommand){
        // Validate the Command
        OrderCompletedEvent orderCompletedEvent = OrderCompletedEvent.builder()
                .orderId(completeOrderCommand.getOrderId())
                .orderStatus(completeOrderCommand.getOrderStatus())
                .build();
        // publish Order Completed Event
        AggregateLifecycle.apply(orderCompletedEvent);
    }

    @EventSourcingHandler
    public  void on(OrderCompletedEvent event){
        this.orderStatus= event.getOrderStatus();
    }


    @CommandHandler
    public void handle(CancelOrderCommand cancelOrderCommand){
        OrderCancelledEvent orderCancelledEvent = new OrderCancelledEvent();
        BeanUtils.copyProperties(cancelOrderCommand,orderCancelledEvent);
        AggregateLifecycle.apply(orderCancelledEvent);
    }


    @EventSourcingHandler
    public  void on(OrderCancelledEvent event){
        this.orderStatus = event.getOrderStatus();
    }
}
