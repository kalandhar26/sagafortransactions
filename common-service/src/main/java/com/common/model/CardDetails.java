package com.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDetails implements Serializable {

    private String  name;
    private String cardNumber;
    private Integer expiryMonth;
    private Integer expiryYear;
    private Integer cvv;
}
