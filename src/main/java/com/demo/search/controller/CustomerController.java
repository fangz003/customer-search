package com.demo.search.controller;


import com.demo.search.model.Customer;
import com.demo.search.service.CustomerSearchService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private final CustomerSearchService customerSearchService;

    @GetMapping("/searchByFirstName")
    @ResponseBody
    public List<Customer> searchCustomersByFirstName(@RequestParam("firstName") String firstName) {
        return null;
    }
}
