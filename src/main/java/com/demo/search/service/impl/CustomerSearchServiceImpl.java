package com.demo.search.service.impl;


import com.demo.search.dao.CustomerDao;
import com.demo.search.mapper.CustomerMapper;
import com.demo.search.model.Customer;
import com.demo.search.repository.CustomerRepository;
import com.demo.search.service.CustomerSearchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerSearchServiceImpl implements CustomerSearchService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public List<Customer> findByFirstName(final String firstName){
        List<CustomerDao> customerDaos = customerRepository.findByFirstName(firstName);
        return null;
    }

    public List<Customer> findByLastName(final String lastName){
        List<CustomerDao> customerDaos = customerRepository.findByFirstName(lastName);
        return null;
    }

    public List<Customer> findByCompanyName(final String companyName){
        List<CustomerDao> customerDaos = customerRepository.findByFirstName(companyName);
        return null;
    }

}
