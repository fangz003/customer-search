package com.demo.search.controller;

import com.demo.search.dao.CustomerDao;
import com.demo.search.mapper.CustomerMapper;
import com.demo.search.model.Customer;
import com.demo.search.repository.CustomerRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerSearchControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    private final CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);


    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        CustomerDao customer1 = createCustomerDao("Adam", "Doe", 1, "CompanyA");
        CustomerDao customer2 = createCustomerDao("John", "Doe", 1, "CompanyA");
        CustomerDao customer3 = createCustomerDao("Adam", "Smith", 1, "CompanyA");
        CustomerDao customer4 = createCustomerDao("John", "Smith", 1, "CompanyA");

        CustomerDao customer5 = createCustomerDao("Adam", "Doe", 2, "CompanyB");
        CustomerDao customer6 = createCustomerDao("Edison", "Smith", 2, "CompanyB");

        List<CustomerDao> customers = List.of(customer1, customer2, customer3, customer4, customer5, customer6);

        customerRepository.saveAll(customers);
    }

    @Test
    void testSearchCustomersByCompanyId_SortedByFirstName_Ascending_Return200() throws Exception {
        List<CustomerDao> customerDaos = customerRepository.findByCompanyId(1, Sort.by(Sort.Direction.ASC, "lastName"));

        List<Customer> customerDtos = customerMapper.toDtos(customerDaos);
        String jsonStr = toJsonString(customerDtos);

        mockMvc.perform(get("/customers/searchByCompanyId")
                        .param("companyId", "1")
                        .param("sortField", "firstName")
                        .param("sortDirection", "asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonStr));
    }

    @Test
    void testSearchCustomersByCompanyId_SortedByFirstName_NotFound_Return404() throws Exception {
        int customerId = 10;
        List<CustomerDao> customerDaos = customerRepository.findByCompanyId(customerId, Sort.by(Sort.Direction.ASC, "lastName"));

        List<Customer> customerDtos = customerMapper.toDtos(customerDaos);

        mockMvc.perform(get("/customers/searchByCompanyId")
                        .param("companyId", String.valueOf(customerId))
                        .param("sortField", "firstName")
                        .param("sortDirection", "asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testSearchCustomersByFirstName_SortedByLastName_Descending_Return200() throws Exception {
        String firstName = "John";

        List<CustomerDao> customerDaos = customerRepository.findByFirstName(firstName, Sort.by(Sort.Direction.DESC, "lastName"));

        List<Customer> customerDtos = customerMapper.toDtos(customerDaos);
        String jsonStr = toJsonString(customerDtos);


        mockMvc.perform(get("/customers/searchByFirstName")
                        .param("firstName", firstName)
                        .param("sortField", "lastName")
                        .param("sortDirection", "desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonStr));
    }

    @Test
    void testSearchCustomersByLastName_SortedByFirstName_Descending_Return200() throws Exception {
        String lastName = "Smith";

        List<CustomerDao> customerDaos = customerRepository.findByLastName(lastName, Sort.by(Sort.Direction.DESC, "firstName"));

        List<Customer> customerDtos = customerMapper.toDtos(customerDaos);
        String jsonStr = toJsonString(customerDtos);


        mockMvc.perform(get("/customers/searchByLastName")
                        .param("lastName", lastName)
                        .param("sortField", "firstName")
                        .param("sortDirection", "desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonStr));
    }

    void testSearchCustomersByCompany_SortedByFirstName_Descending() throws Exception {
        mockMvc.perform(get("/api/customers/searchByCompanyId")
                        .param("companyName", "CompanyA")
                        .param("sortField", "firstName")
                        .param("sortDirection", "desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'firstName':'John'},{'firstName':'Jane'}]"));
    }

    private static CustomerDao createCustomerDao(String firstName, String lastName, Integer companyId, String companyName) {
        return CustomerDao.builder()
                .firstName(firstName)
                .lastName(lastName)
                .companyName(companyName)
                .companyId(companyId)
                .build();
    }

    private String toJsonString(List<Customer> customers) {
        Gson gson = new Gson();
        return gson.toJson(customers);
    }
}
