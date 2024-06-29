package com.demo.search.mapper;

import com.demo.search.dao.CompanyDao;
import com.demo.search.dao.CustomerDao;
import com.demo.search.model.Company;
import com.demo.search.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class) // JUnit 5
@ContextConfiguration(classes = {
        CustomerMapperImpl.class,
        CompanyMapperImpl.class
})
public class CustomerMapperTest {

    private CustomerMapper customerMapper = null;

    private CompanyMapper companyMapper = Mappers.getMapper(CompanyMapper.class);
    @BeforeEach
    public void setUp() {
        customerMapper = new CustomerMapperImpl(companyMapper);
    }

    @Test
    public void testToDTO() {
        CustomerDao customerDao = new CustomerDao();
        customerDao.setId(1);
        customerDao.setFirstName("John");
        customerDao.setLastName("Doe");

        Customer customer = customerMapper.toDTO(customerDao);

        assertEquals(customerDao.getId(), customer.getId());
        assertEquals(customerDao.getFirstName(), customer.getFirstName());
        assertEquals(customerDao.getLastName(), customer.getLastName());
    }

    @Test
    public void testToEntity() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("Jane");
        customer.setLastName("Smith");

        CustomerDao customerDao = customerMapper.toEntity(customer);

        assertEquals(customer.getId(), customerDao.getId());
        assertEquals(customer.getFirstName(), customerDao.getFirstName());
        assertEquals(customer.getLastName(), customerDao.getLastName());
    }

    @Test
    public void testToEntities() {
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setFirstName("John");
        customer1.setLastName("Doe");

        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setFirstName("Jane");
        customer2.setLastName("Smith");

        List<Customer> customerList = Arrays.asList(customer1, customer2);

        List<CustomerDao> customerDaoList = customerMapper.toEntities(customerList);

        assertEquals(customerList.size(), customerDaoList.size());
        assertEquals(customerList.get(0).getId(), customerDaoList.get(0).getId());
        assertEquals(customerList.get(0).getFirstName(), customerDaoList.get(0).getFirstName());
        assertEquals(customerList.get(0).getLastName(), customerDaoList.get(0).getLastName());
        assertEquals(customerList.get(1).getId(), customerDaoList.get(1).getId());
        assertEquals(customerList.get(1).getFirstName(), customerDaoList.get(1).getFirstName());
        assertEquals(customerList.get(1).getLastName(), customerDaoList.get(1).getLastName());
    }

    @Test
    public void testToDtos() {
        CustomerDao customerDao1 = new CustomerDao();
        customerDao1.setId(1);
        customerDao1.setFirstName("John");
        customerDao1.setLastName("Doe");

        CustomerDao customerDao2 = new CustomerDao();
        customerDao2.setId(2);
        customerDao2.setFirstName("Jane");
        customerDao2.setLastName("Smith");

        List<CustomerDao> customerDaoList = Arrays.asList(customerDao1, customerDao2);

        List<Customer> customerList = customerMapper.toDtos(customerDaoList);

        assertEquals(customerDaoList.size(), customerList.size());
        assertEquals(customerDaoList.get(0).getId(), customerList.get(0).getId());
        assertEquals(customerDaoList.get(0).getFirstName(), customerList.get(0).getFirstName());
        assertEquals(customerDaoList.get(0).getLastName(), customerList.get(0).getLastName());
        assertEquals(customerDaoList.get(1).getId(), customerList.get(1).getId());
        assertEquals(customerDaoList.get(1).getFirstName(), customerList.get(1).getFirstName());
        assertEquals(customerDaoList.get(1).getLastName(), customerList.get(1).getLastName());
    }
}
