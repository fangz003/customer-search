package com.demo.search.mapper;

import com.demo.search.dao.CompanyDao;
import com.demo.search.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    Company toDTO(CompanyDao company);

    CompanyDao toEntity(Company companyDTO);

    List<CompanyDao> toEntities(List<Company> customerDTOs);

    List<Company> toDtos(List<CompanyDao> companyDaos);
}