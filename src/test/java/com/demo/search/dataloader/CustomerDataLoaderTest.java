package com.demo.search.dataloader;

import com.demo.search.dao.CustomerDao;
import com.demo.search.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ComponentScan(basePackages = {"com.demo.search"})
public class CustomerDataLoaderTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testDataLoader() {
        List<CustomerDao> customers = customerRepository.findAll();

        assertThat(customers).hasSize(500);
    }
}
