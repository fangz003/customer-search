package com.demo.search.controller;


import com.demo.search.exception.ResourceNotFoundException;
import com.demo.search.model.Customer;
import com.demo.search.service.CustomerSearchService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers/search")
@AllArgsConstructor
public class CustomerSearchController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerSearchController.class);

    @Autowired
    private final CustomerSearchService customerSearchService;

    @GetMapping("/searchByFirstName")
    @ResponseBody
    public List<Customer> searchCustomersByFirstName(@RequestParam String firstName,
                                                     @RequestParam(required = false) String sortField,
                                                     @RequestParam(defaultValue = "asc") String sortDirection) {
        List<Customer> customers = customerSearchService.findByFirstName(firstName, sortField, sortDirection);
        if (customers.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Failed to search customer by first name:  %s ", firstName));
        }
        return customers;
    }

    @GetMapping("/searchByLastName")
    @ResponseBody
    public List<Customer> searchCustomersByLastName(@RequestParam String lastName,
                                                    @RequestParam(required = false) String sortField,
                                                    @RequestParam(defaultValue = "asc") String sortDirection) {
        List<Customer> customers = customerSearchService.findByLastName(lastName, sortField, sortDirection);

        if (customers.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Failed to search customer by last name:  %s ", lastName));
        }
        return customers;
    }

    @GetMapping("/searchByCompanyId")
    @ResponseBody
    public List<Customer> searchCustomersByCompanyId(@RequestParam Integer companyId,
                                                     @RequestParam(required = false) String sortField,
                                                     @RequestParam(defaultValue = "asc") String sortDirection) {
        List<Customer> customers = customerSearchService.findByCompanyId(companyId, sortField, sortDirection);

        if (customers.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Failed to search customer by company id:  %d ", companyId));
        }
        return customers;
    }

    @GetMapping("/listAll")
    @ResponseBody

    public List<Customer> listAllCustomers() {
        return customerSearchService.listAll();
    }
}
