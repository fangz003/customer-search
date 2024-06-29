package com.demo.search.service;

import com.demo.search.dao.CompanyDao;
import com.demo.search.dao.CustomerDao;
import com.demo.search.mapper.CompanyMapper;
import com.demo.search.mapper.CustomerMapperImpl;
import com.demo.search.model.Customer;
import com.demo.search.repository.CustomerRepository;
import com.demo.search.service.impl.CustomerSearchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

public class CustomerSearchServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CompanyMapper companyMapper;

    @InjectMocks
    private CustomerMapperImpl customerMapper;

    private CustomerSearchService customerSearchService;

    private List<CustomerDao> customers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerSearchService = new CustomerSearchServiceImpl(customerRepository, customerMapper);
        customers = createCustomerDaos();
    }

    @Test
    void testGetCustomersByCompanyIdSorted() {
        given(customerRepository.findByCompanyId(anyInt(), any(Sort.class))).willReturn(customers);

        // when
        List<Customer> result = customerSearchService.findByCompanyId(anyInt(), anyString(), "asc");

        // then
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
    }
    @Test
    void testGetCustomersByFirstNameSorted() {

        final var fistName = "John";
        // given
        given(customerRepository.findByFirstName(anyString(), any(Sort.class))).willReturn(List.of(customers.get(0)));

        // when
        List<Customer> result = customerSearchService.findByFirstName(anyString(), anyString(), "asc");

        // then
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
    }

    @Test
    void testGetCustomersByLastNameSorted() {

        // given
        List<CustomerDao> customerDaos = customers.subList(1,2);

        given(customerRepository.findByLastName(anyString(), any(Sort.class))).willReturn(customerDaos);

        // when
        List<Customer> result = customerSearchService.findByLastName(anyString(), anyString(), "asc");

        // then
        assertEquals(1, result.size());
        assertEquals("Jane", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
    }

    private List<CustomerDao> createCustomerDaos(){

        CompanyDao companyDao = CompanyDao.builder().id(1).name("CompanyA").build();

        CustomerDao customer1 = CustomerDao.builder()
                .firstName("John")
                .lastName("Doe")
                .company(companyDao)
                .build();

        CustomerDao customer2 = CustomerDao.builder()
                .firstName("Jane")
                .lastName("Doe")
                .company(companyDao)
                .build();

        return  Arrays.asList(customer1, customer2);
    }
}


