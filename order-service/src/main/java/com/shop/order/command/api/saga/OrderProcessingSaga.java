package com.shop.order.command.api.saga;

import com.common.commands.*;
import com.common.events.*;
import com.common.model.User;
import com.common.queries.GetUserPaymentDetailsQuery;
import com.shop.order.command.api.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.UUID;

@Saga
@Slf4j
public class OrderProcessingSaga implements Serializable  {

    @Autowired
    private transient   CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    public OrderProcessingSaga() {
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {

        log.info("OrderCreatedEvent in Saga for Order Id : {}", event.getOrderId());

        GetUserPaymentDetailsQuery getUserPaymentDetailsQuery = new GetUserPaymentDetailsQuery(event.getUserId());
        User user = null;
        try {
            user = queryGateway.query(
                    getUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)
            ).join();

            if(user != null) {
                ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand.builder()
                        .orderId(event.getOrderId())
                        .paymentId(UUID.randomUUID().toString())
                        .cardDetails(user.getCardDetails())
                        .build();

                commandGateway.sendAndWait(validatePaymentCommand);
            }else{
                log.error("User not found for userId: {}", event.getUserId());
            }


        } catch (Exception e) {
            log.error(e.getMessage());
            // Start Compensating transaction
            cancelOrderCommand(event.getOrderId());
        }



    }

    private void cancelOrderCommand(String orderId) {
        CancelOrderCommand cancelOrderCommand = new CancelOrderCommand(orderId);
        commandGateway.send(cancelOrderCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent event) {

        log.info("PaymentProcessedEvent in Saga for Order Id : {}", event.getOrderId());
        try {
            ShipOrderCommand shipOrderCommand = ShipOrderCommand.builder()
                    .orderId(event.getOrderId())
                    .shipmentId(UUID.randomUUID().toString())
                    .build();
            commandGateway.send(shipOrderCommand);
        } catch (Exception e) {
            log.info(e.getMessage());
            // Start the Compensation transaction
            cancelPaymentCommand(event);
        }
    }

    private void cancelPaymentCommand(PaymentProcessedEvent event) {

        CancelPaymentCommand cancelPaymentCommand = new CancelPaymentCommand(event.getPaymentId(), event.getOrderId());
        commandGateway.send(cancelPaymentCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderShippedEvent event) {

        CompleteOrderCommand completeOrderCommand = CompleteOrderCommand.builder()
                .orderId(event.getOrderId())
                .OrderStatus("APPROVED")
                .build();

        commandGateway.send(completeOrderCommand);

    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(OrderCompletedEvent event) {

        log.info("OrderCompletedEvent in Saga for Order Id : {}", event.getOrderId());
        try {

        } catch (Exception e) {
            log.info(e.getMessage());
            // Compensation logic if failure
        }
    }


    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(OrderCancelledEvent event){
        log.info("OrderCancelledEvent in Saga for Order Id : {}", event.getOrderId());
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(PaymentCancelledEvent event){
        log.info("PaymentCancelledEvent in Saga for Order Id : {}", event.getOrderId());
        cancelOrderCommand(event.getOrderId());
    }
}
