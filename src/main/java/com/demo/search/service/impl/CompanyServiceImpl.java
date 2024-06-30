package com.demo.search.service.impl;

import com.demo.search.mapper.CompanyMapper;
import com.demo.search.model.Company;
import com.demo.search.repository.CompanyRepository;
import com.demo.search.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public List<Company> listAllCompanies(){
        return companyMapper.toDtos(companyRepository.findAll());
    }

}
