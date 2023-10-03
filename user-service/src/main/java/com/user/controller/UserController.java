package com.user.controller;

import com.common.model.User;
import com.common.queries.GetUserPaymentDetailsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@RequestMapping("/users")
public class UserController implements Serializable {

    private transient QueryGateway queryGateway;

    public UserController() {
    }

    @Autowired
    public UserController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }


    @GetMapping("/{userId}")
    public User getUserPaymentDetails(@PathVariable String userId){

        GetUserPaymentDetailsQuery getUserPaymentDetailsQuery = new GetUserPaymentDetailsQuery(userId);

        return queryGateway.query(getUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();

    }
}
