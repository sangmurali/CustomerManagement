package com.springboot.microservice.address;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "address-service")
@RibbonClient(name = "address-service")
public interface AddressServiceProxy {
//    @GetMapping("/address/{id}")
//    Address getAddress(@PathVariable("id") String id);
    @GetMapping("/address")
    List<Address> getAddressesForCustomer(@RequestParam("customerId") Long customerId);
    @PostMapping("/address")
    Long saveAddress(Address address);
}