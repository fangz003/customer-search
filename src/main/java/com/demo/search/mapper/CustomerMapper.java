package com.demo.search.mapper;

import com.demo.search.model.Customer;
import com.demo.search.dao.CustomerDao;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * CustomerMapper uses MapStruct to convert between CustomerDao and Customer (dto class)
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer toDTO(CustomerDao customerDao);

    CustomerDao toEntity(Customer customerDTO);

    List<CustomerDao> toEntities(List<Customer> customerDTOs);

    List<Customer> toDtos(List<CustomerDao> customerDao);
}
