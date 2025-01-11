package com.ecommpet.customer.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Customer {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String thirdName;
    private String email;
    private Address adress;
}
