package com.demo.search.service;

import com.demo.search.dao.CustomerDao;
import com.demo.search.mapper.CustomerMapper;
import com.demo.search.model.Customer;
import com.demo.search.repository.CustomerRepository;
import com.demo.search.service.impl.CustomerSearchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

public class CustomerSearchServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Spy
    private final CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    @InjectMocks
    private CustomerSearchServiceImpl customerSearchService;

    private List<CustomerDao> customers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        CustomerDao customer1 = new CustomerDao();
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        customer1.setCompanyId(1);
        customer1.setCompanyName("CompanyA");

        CustomerDao customer2 = new CustomerDao();
        customer2.setFirstName("Jane");
        customer2.setLastName("Doe");
        customer2.setCompanyId(1);
        customer2.setCompanyName("CompanyA");

        customers = Arrays.asList(customer1, customer2);
    }

    @Test
    void testGetCustomersByCompanyIdSorted() {
        // given
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

        final var lastName = "John";
        // given
        given(customerRepository.findByLastName(anyString(), any(Sort.class))).willReturn(List.of(customers.get(1)));

        // when
        List<Customer> result = customerSearchService.findByLastName(anyString(), anyString(), "asc");

        // then
        assertEquals(1, result.size());
        assertEquals("Jane", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
    }
}


