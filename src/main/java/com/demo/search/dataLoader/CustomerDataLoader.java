package com.demo.search.dataLoader;

import com.demo.search.dao.CustomerDao;
import com.demo.search.repository.CustomerRepository;
import com.github.javafaker.Faker;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * Load fake data into Customer entity table
 */
@Component
@AllArgsConstructor
public class CustomerDataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CustomerDataLoader.class);

    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        List<CustomerDao> customerDaos = new ArrayList<>();

        logger.info("Load customer data with fake data");
        if (customerRepository.count() > 0) {
            return;
        }

        //Generate 100 fake customers in DB.
        int i = 0;
        while(i < 100) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String companyName = faker.company().name();

            if(isFakeDataValid(firstName) && isFakeDataValid(lastName) && isFakeDataValid(companyName)){
                CustomerDao customerDao = CustomerDao.builder().firstName(firstName)
                        .lastName(lastName)
                        .companyName(companyName)
                        .build();
                customerDaos.add(customerDao);
                i++;
            }
        }

        customerRepository.saveAll(customerDaos);
    }

    /**
     * Fake dta can not be null
     * @param data
     * @return
     */
    private boolean isFakeDataValid(final String data ){
        return StringUtils.isNotEmpty(data);
    }
}
