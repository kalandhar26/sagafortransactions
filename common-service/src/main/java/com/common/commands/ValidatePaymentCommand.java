package com.common.commands;

import com.common.model.CardDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidatePaymentCommand implements Serializable {

    private String paymentId;
    private String orderId;
    private CardDetails  cardDetails;
}
