package com.user.projection;

import com.common.model.CardDetails;
import com.common.model.User;
import com.common.queries.GetUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {

    @QueryHandler
    public User userPaymentDetails(GetUserPaymentDetailsQuery query){

        // Get Details from DB

        CardDetails cardDetails = CardDetails.builder()
                .cardNumber("9807654321")
                .cvv(123)
                .name("BabaKalandhar")
                .expiryMonth(10)
                .expiryYear(2029)
                .build();

        User user = User.builder()
                .cardDetails(cardDetails)
                .firstName("Baba")
                .lastName("kalandhar")
                .userId(query.getUserId())
                .build();

        return user;

    }
}
