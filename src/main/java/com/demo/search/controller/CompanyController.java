package com.demo.search.controller;

import com.demo.search.model.Company;
import com.demo.search.service.CompanyService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
@AllArgsConstructor
public class CompanyController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerSearchController.class);
    private final CompanyService companyService;

    @GetMapping("/listAll")
    @ResponseBody
    public List<Company> listAllCompanies(){
        return companyService.listAllCompanies();
    }
}
