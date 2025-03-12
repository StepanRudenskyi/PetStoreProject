package org.example.petstore.mapper;

import org.example.petstore.dto.DepartmentDto;
import org.example.petstore.model.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    Department toEntity(DepartmentDto departmentDto);
    DepartmentDto toDto(Department department);
}
