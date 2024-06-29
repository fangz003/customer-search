package com.demo.search.dataLoader;

import com.demo.search.dao.CompanyDao;
import com.demo.search.dao.CustomerDao;
import com.demo.search.repository.CompanyRepository;
import com.demo.search.repository.CustomerRepository;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * CustomerDataLoader load fake data into Customer entity table
 */
@Component
@AllArgsConstructor
public class CustomerDataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CustomerDataLoader.class);

    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;

    public final static int DATA_SET_SiZE = 500;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        faker.expression("[A-Za-z]+");
        List<CustomerDao> customerDaos = new ArrayList<>();

        logger.info("Start to load customer data with fake data");
        if (customerRepository.count() > 0) {
            return;
        }

        //Generate 500 fake customers in DB.
        int i = 0;
        String firstName = null;
        String lastName = null;

        List<CompanyDao> companyDaosPersisted = createCompanyData();
        Random random = new Random();

        while (i < DATA_SET_SiZE) {
            if (i % 10 == 0) {
                firstName = faker.name().firstName();
            }
            if (i % 20 == 0) {
                lastName = faker.name().lastName();
            }
            int randomIndex = random.nextInt(companyDaosPersisted.size());

            CustomerDao customerDao = CustomerDao.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .company(companyDaosPersisted.get(randomIndex))
                    .build();
            customerDaos.add(customerDao);
            i++;
        }

        customerRepository.saveAll(customerDaos);

        final var size = customerRepository.count();
        logger.info(String.format("{0} customers have been loaded to customer table", size));
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
