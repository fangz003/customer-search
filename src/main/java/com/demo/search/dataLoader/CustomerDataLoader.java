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

        logger.info("Start to load customer data with fake data");
        cleanUpData();
        List<CompanyDao> companyDaosPersisted = createCompanyData(faker);
        createCustomerData(faker, companyDaosPersisted);

        final var size = customerRepository.count();
        logger.info(String.format("%d customers have been loaded to customer table", size));
    }

    private List<CompanyDao> createCompanyData(Faker faker) {
        List<CompanyDao> companyDaos = new ArrayList<>();
        for (int j = 0; j < 8; j++) {
            companyDaos.add(CompanyDao.builder()
                    .name(faker.company().name())
                    .build());
        }
        return companyRepository.saveAll(companyDaos);
    }

    private List<CustomerDao> createCustomerData(Faker faker, List<CompanyDao> companyDaosPersisted){//Generate 500 fake customers in DB.
        List<CustomerDao> customerDaos = new ArrayList<>();

        int i = 0;
        String firstName = null;
        String lastName = null;

        Random random = new Random();

        while (i < DATA_SET_SiZE) {
            if (i % 3 == 0) {
                firstName = faker.name().firstName();
            }
            if (i % 5 == 0) {
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

        return customerRepository.saveAll(customerDaos);
    }

    private void cleanUpData(){
        customerRepository.deleteAll();;
        companyRepository.deleteAll();;
    }

}
