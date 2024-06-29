package com.demo.search.repository;

import com.demo.search.dao.CompanyDao;
import com.demo.search.dao.CustomerDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ComponentScan(basePackages = {"com.demo.search"})
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    void setUp() {

        customerRepository.deleteAll();
        companyRepository.deleteAll();

        CompanyDao companyADao = CompanyDao.builder().name("companyA").build();
        CompanyDao companyBDao = CompanyDao.builder().name("companyB").build();
        CompanyDao persistedCompanyA = companyRepository.save(companyADao);
        CompanyDao persistedCompanyB = companyRepository.save(companyBDao);

        CustomerDao customer1 = createCustomerDao("Adam", "Doe",  persistedCompanyA.getId(), "CompanyA");
        CustomerDao customer2 = createCustomerDao("John", "Doe", persistedCompanyA.getId(),  "CompanyA");
        CustomerDao customer3 = createCustomerDao("Adam", "Smith",  persistedCompanyA.getId(), "CompanyA");
        CustomerDao customer4 = createCustomerDao("John", "Smith", persistedCompanyA.getId(),"CompanyA");

        CustomerDao customer5 = createCustomerDao("Adam", "Doe", persistedCompanyB.getId(), "CompanyB");
        CustomerDao customer6 = createCustomerDao("Edison", "Smith", persistedCompanyB.getId(), "CompanyB");

        List<CustomerDao> customers = List.of(customer1, customer2, customer3, customer4, customer5, customer6);

        customerRepository.saveAll(customers);
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    void testFindByCompanyId_SortedByLastName_Descending() {
        //given
        int companyId = companyRepository.findAll().get(0).getId();

        // when
        List<CustomerDao> result = customerRepository.findByCompanyId(companyId, Sort.by(Sort.Direction.DESC, "lastName"));

        // then
        assertEquals(4, result.size());
        assertEquals("Smith", result.get(0).getLastName());
    }

    @Test
    void testFindByCompanyId_SortedByLastName_Ascending() {
        int companyId = companyRepository.findAll().get(0).getId();
        // when
        List<CustomerDao> result = customerRepository.findByCompanyId(companyId, Sort.by(Sort.Direction.ASC, "lastName"));

        // then
        assertEquals(4, result.size());
        assertEquals("Doe", result.get(0).getLastName());
    }

    @Test
    void testFindByCompanyId_SortedByFirstName_Ascending() {

        //given
        int companyId = companyRepository.findAll().get(0).getId();

        // when
        List<CustomerDao> result = customerRepository.findByCompanyId(companyId, Sort.by(Sort.Direction.ASC, "firstName"));

        // then
        assertEquals(4, result.size());
        assertEquals("Doe", result.get(0).getLastName());
    }

    @Test
    void testFindByCompanyId_SortedByFirstName_Descending() {
        //given
        int companyId = companyRepository.findAll().get(0).getId();

        // when
        List<CustomerDao> result = customerRepository.findByCompanyId(companyId, Sort.by(Sort.Direction.DESC, "firstName"));

        // then
        assertEquals(4, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    void testFindByFirstName_SortedByLastName_Descending() {
        // when
        List<CustomerDao> result = customerRepository.findByFirstName("John", Sort.by(Sort.Direction.DESC, "lastName"));

        // then
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Smith", result.get(0).getLastName());
    }

    @Test
    void testFindByFirstName_SortedByLastName_Ascending() {
        // when
        List<CustomerDao> result = customerRepository.findByFirstName("John", Sort.by(Sort.Direction.ASC, "lastName"));

        // then
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
    }

    @Test
    void testFindByLastName_SortedByFirstName_Ascending() {
        // when
        List<CustomerDao> result = customerRepository.findByLastName("Smith", Sort.by(Sort.Direction.ASC, "lastName"));

        // then
        assertEquals(3, result.size());
        assertEquals("Adam", result.get(0).getFirstName());
        assertEquals("Smith", result.get(0).getLastName());
    }

    @Test
    void testFindByLastName_SortedByFirstName_Descending() {
        // when
        List<CustomerDao> result = customerRepository.findByLastName("Smith", Sort.by(Sort.Direction.DESC, "lastName"));

        // then
        assertEquals(3, result.size());
        assertEquals("Adam", result.get(0).getFirstName());
        assertEquals("Smith", result.get(0).getLastName());
    }

    private static CustomerDao createCustomerDao(String firstName, String lastName, Integer companyId, String companyName) {
        return CustomerDao.builder()
                .firstName(firstName)
                .lastName(lastName)
                .company(CompanyDao.builder().
                        id(companyId)
                        .name(companyName).build())
                .build();
    }
}
