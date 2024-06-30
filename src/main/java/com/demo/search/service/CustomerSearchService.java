package com.demo.search.service;

import com.demo.search.model.Customer;

import java.util.List;

public interface CustomerSearchService {

    List<Customer> findByFirstName(final String firstName, String sortField, String sortDirection);

    List<Customer> findByLastName(final String firstName, String sortField, String sortDirection);

    List<Customer> findByCompanyId(final Integer companyId, String sortField, String sortDirection);

    List<Customer> listAll();

}
