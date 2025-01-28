package com.ecommpet.customer.mapper;

import com.ecommpet.customer.dto.CustomerRequest;
import com.ecommpet.customer.dto.CustomerResponse;
import com.ecommpet.customer.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toCustomer(CustomerRequest request){
        return Customer.builder()
                .id(request.id())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .thirdName(request.thirdName())
                .email(request.email())
                .adress(request.adress())
                .build();
    }

    public CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getThirdName(),
                customer.getEmail(),
                customer.getAdress()
        );
    }
}
