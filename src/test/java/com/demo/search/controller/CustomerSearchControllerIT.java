package com.demo.search.controller;

import com.demo.search.dao.CompanyDao;
import com.demo.search.dao.CustomerDao;
import com.demo.search.mapper.CompanyMapper;
import com.demo.search.mapper.CustomerMapper;
import com.demo.search.mapper.CustomerMapperImpl;
import com.demo.search.model.Customer;
import com.demo.search.repository.CompanyRepository;
import com.demo.search.repository.CustomerRepository;
import com.github.javafaker.Faker;
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

import java.util.ArrayList;
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

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        companyRepository.deleteAll();

        CompanyMapper companyMapper = Mappers.getMapper(CompanyMapper.class);
        List<CompanyDao> companyDaos = createCompanyData();
        CompanyDao company1 = companyDaos.get(0);
        CompanyDao company2 = companyDaos.get(1);

        CustomerDao customer1 = createCustomerDao("Adam", "Doe", company1);
        CustomerDao customer2 = createCustomerDao("John", "Doe", company1);
        CustomerDao customer3 = createCustomerDao("Adam", "Smith", company1);
        CustomerDao customer4 = createCustomerDao("John", "Smith", company1);

        CustomerDao customer5 = createCustomerDao("Adam", "Doe", company2);
        CustomerDao customer6 = createCustomerDao("Edison", "Smith", company2);

        List<CustomerDao> customers = List.of(customer1, customer2, customer3, customer4, customer5, customer6);

        customerRepository.saveAll(customers);
    }

    @Test
    void testSearchCustomersByCompanyId_SortedByFirstName_Ascending_Return200() throws Exception {

        int companyId = companyRepository.findAll().get(0).getId();

        List<CustomerDao> customerDaos = customerRepository.findByCompanyId(companyId, Sort.by(Sort.Direction.ASC, "lastName"));

        List<Customer> customerDtos = customerMapper.toDtos(customerDaos);
        String jsonStr = toJsonString(customerDtos);

        mockMvc.perform(get("/customers/search/searchByCompanyId")
                        .param("companyId", String.valueOf(companyId))
                        .param("sortField", "firstName")
                        .param("sortDirection", "asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonStr));
    }

    @Test
    void testSearchCustomersByCompanyId_SortedByFirstName_NotFound_Return404() throws Exception {
        int companyId = Integer.MAX_VALUE;
        List<CustomerDao> customerDaos = customerRepository.findByCompanyId(companyId, Sort.by(Sort.Direction.ASC, "lastName"));

        List<Customer> customerDtos = customerMapper.toDtos(customerDaos);

        mockMvc.perform(get("/customers/search/searchByCompanyId")
                        .param("companyId", String.valueOf(companyId))
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


        mockMvc.perform(get("/customers/search/searchByFirstName")
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


        mockMvc.perform(get("/customers/search/searchByLastName")
                        .param("lastName", lastName)
                        .param("sortField", "firstName")
                        .param("sortDirection", "desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonStr));
    }

    private static CustomerDao createCustomerDao(String firstName, String lastName, CompanyDao companyDao) {
        return CustomerDao.builder()
                .firstName(firstName)
                .lastName(lastName)
                .company(companyDao)
                .build();
    }

    private String toJsonString(List<Customer> customers) {
        Gson gson = new Gson();
        return gson.toJson(customers);
    }

    private List<CompanyDao> createCompanyData() {
        Faker faker = new Faker();
        List<CompanyDao> companyDaos = new ArrayList<>();
        for (int j = 0; j < 8; j++) {
            companyDaos.add(CompanyDao.builder()
                    .name(faker.company().name())
                    .build());
        }
        return companyRepository.saveAll(companyDaos);
    }
}
