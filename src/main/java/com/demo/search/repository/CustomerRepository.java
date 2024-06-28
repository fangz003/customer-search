package com.demo.search.repository;

import com.demo.search.dao.CustomerDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * A repository to persist and load customers by search criteria
 */

@Repository
public interface CustomerRepository extends JpaRepository<CustomerDao, Long> {

    List<CustomerDao> findByFirstName(String firstName);

    List<CustomerDao> findByLastName(String lastName);

    List<CustomerDao> findByCompanyName(String companyName);
}
