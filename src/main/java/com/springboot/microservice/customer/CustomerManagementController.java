package com.springboot.microservice.customer;

import com.springboot.microservice.address.Address;
import com.springboot.microservice.address.AddressServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CustomerManagementController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private Environment environment;

    @Autowired
    private AddressServiceProxy proxy;

    @Autowired
    private CustomerRepository repository;

    @GetMapping("/customer/{id}")
    public Customer get(@PathVariable Long id) {
        Optional<Customer> customerOpt = repository.findById(id);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            List<Address> addresses = proxy.getAddressesForCustomer(id);
            customer.setAddresses(addresses);
            customer.setPort(
                    Integer.parseInt(environment.getProperty("local.server.port")));return customer;
        }

        return null;//todo exception 404
    }

    @PostMapping("/customer")
    public Long save(@Valid @RequestBody Customer customer) {

        Customer savedCustomer = repository.save(customer);
        for(Address address:customer.getAddresses()) {
            address.setCustomerId(savedCustomer.getId());
            proxy.saveAddress(address);
        }
        return savedCustomer.getId();
    }


}