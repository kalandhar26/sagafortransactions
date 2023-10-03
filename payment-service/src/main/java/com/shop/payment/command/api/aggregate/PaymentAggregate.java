package com.shop.payment.command.api.aggregate;

import com.common.commands.CancelPaymentCommand;
import com.common.commands.ValidatePaymentCommand;
import com.common.events.PaymentCancelledEvent;
import com.common.events.PaymentProcessedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@Slf4j
public class PaymentAggregate {

    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private String paymentStatus;

    public PaymentAggregate() {
    }

    @CommandHandler
    public PaymentAggregate(ValidatePaymentCommand validatePaymentCommand) {

        // Validate Payment Details

        // Publish the Payment Processed Event
        log.info(" Executing ValidatePaymentCommand for " + "Order Id : {} and Payment Id: {}",
                validatePaymentCommand.getOrderId(),
                validatePaymentCommand.getPaymentId());

        PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent(
                validatePaymentCommand.getOrderId(), validatePaymentCommand.getPaymentId()
        );

        AggregateLifecycle.apply(paymentProcessedEvent);

        log.info("PaymentProcessedEvent Applied");
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent event){
        this.orderId = event.getOrderId();
        this.paymentId= event.getPaymentId();
    }

    @CommandHandler
    public  void handle(CancelPaymentCommand cancelPaymentCommand){
        PaymentCancelledEvent paymentCancelledEvent = new PaymentCancelledEvent();
        BeanUtils.copyProperties(cancelPaymentCommand, paymentCancelledEvent);
        AggregateLifecycle.apply(paymentCancelledEvent);
    }


    @EventSourcingHandler
    public  void on (PaymentCancelledEvent event){
        this.paymentStatus = event.getPaymentStatus();
    }
}
