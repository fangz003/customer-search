package com.demo.search.service.impl;

import com.demo.search.dao.CustomerDao;
import com.demo.search.mapper.CustomerMapper;
import com.demo.search.model.Customer;
import com.demo.search.repository.CustomerRepository;
import com.demo.search.service.CustomerSearchService;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerSearchServiceImpl implements CustomerSearchService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    /**
     * Find customer by first name
     *
     * @param firstName
     * @param sortField
     * @param sortDirection asc or desc
     * @return
     */
    public List<Customer> findByFirstName(final String firstName, final String sortField, final String sortDirection) {
        Sort sort = getSort(sortField, sortDirection);
        List<CustomerDao> customerDaos = customerRepository.findByFirstNameStartsWithIgnoreCase(firstName, sort);
        return customerMapper.toDtos(customerDaos);
    }

    /**
     * Find customer by last name
     *
     * @param lastName
     * @param sortField
     * @param sortDirection asc or desc
     * @return
     */
    public List<Customer> findByLastName(final String lastName, final String sortField, final String sortDirection) {
        Sort sort = getSort(sortField, sortDirection);
        List<CustomerDao> customerDaos = customerRepository.findByLastNameStartsWithIgnoreCase(lastName, sort);
        return customerMapper.toDtos(customerDaos);
    }

    /**
     * Find customer by company ID
     *
     * @param companyId
     * @param sortField
     * @param sortDirection asc or desc
     * @return
     */
    public List<Customer> findByCompanyId(final Integer companyId, final String sortField, final String sortDirection) {
        Sort sort = getSort(sortField, sortDirection);
        List<CustomerDao> customerDaos = customerRepository.findByCompanyId(companyId, sort);
        return customerMapper.toDtos(customerDaos);
    }

    public List<Customer> listAll(){
        return customerMapper.toDtos(customerRepository.findAll());
    }

    private Sort getSort(String sortField, String sortDirection) {
        if (StringUtils.isEmpty(sortField)) {
            return Sort.unsorted();
        }
        return Sort.by(Sort.Direction.fromString(sortDirection), sortField);
    }

}
