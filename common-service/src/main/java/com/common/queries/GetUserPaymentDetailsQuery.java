package com.common.queries;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetUserPaymentDetailsQuery implements Serializable {


    private String userId;

    public GetUserPaymentDetailsQuery() {
    }

    public GetUserPaymentDetailsQuery(String userId) {
        this.userId = userId;
    }
}
