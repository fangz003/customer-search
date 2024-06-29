package com.demo.search.mapper;

import com.demo.search.dao.CustomerDao;
import com.demo.search.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * CustomerMapper uses MapStruct to convert between CustomerDao and Customer (dto class)
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    /**
     * Mapper between customer dao and customer dto
     *
     * @param customerDao
     * @return
     */
    Customer toDTO(CustomerDao customerDao);

    /**
     * Mapper between customer dto and customer dao
     *
     * @param customerDTO
     * @return
     */
    CustomerDao toEntity(Customer customerDTO);

    /**
     * Mapper between a list of customer dtos and a list of customer daos
     *
     * @param customerDTOs
     * @return
     */
    List<CustomerDao> toEntities(List<Customer> customerDTOs);

    /**
     * Mapper between a list of customer daos and a list of customer dtos
     *
     * @param customerDao
     * @return
     */
    List<Customer> toDtos(List<CustomerDao> customerDao);
}
