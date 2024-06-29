package com.demo.search.repository;

import com.demo.search.dao.CompanyDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyDao, Long> {

}
