package com.ecommpet.customer.service;

import ch.qos.logback.core.util.StringUtil;
import com.ecommpet.customer.dto.CustomerRequest;
import com.ecommpet.customer.dto.CustomerResponse;
import com.ecommpet.customer.entity.Customer;
import com.ecommpet.customer.exception.CustomerNotFoundException;
import com.ecommpet.customer.mapper.CustomerMapper;
import com.ecommpet.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest request) {
        Customer savedCustomer = customerRepository.save(customerMapper.toCustomer(request));
        return savedCustomer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        Customer customer = customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cant update customer:: No such customer with ID:: %s", request.id())
                ));
        mergerCustomer(request,customer);
        customerRepository.save(customer);
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return customerRepository.existsById(customerId);
    }

    public CustomerResponse findById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::fromCustomer)
                .orElseThrow(()->new CustomerNotFoundException(
                        String.format("Cant update customer:: No such customer with ID:: %s", customerId
                )));
    }

    public void delete(String customerId) {
        customerRepository.deleteById(customerId);
    }

    private void mergerCustomer(CustomerRequest request,Customer customer){
        if (StringUtils.isNotBlank(request.firstName())) {
           customer.setFirstName(request.firstName());
        }
        if (StringUtils.isNotBlank(request.lastName())) {
            customer.setLastName(request.lastName());
        }
        if (StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if (request.email() != null) {
            customer.setEmail(request.email());
        }
    }



}
