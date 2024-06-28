package com.demo.search.service;

import com.demo.search.model.Customer;

import java.util.List;

public interface CustomerSearchService {

    List<Customer> findByFirstName(final String firstName);

    List<Customer> findByLastName(final String firstName);

    List<Customer> findByCompanyName(final String companyName);

}
