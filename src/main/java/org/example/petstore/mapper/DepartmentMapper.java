package org.example.petstore.mapper;

import org.example.petstore.dto.department.DepartmentDto;
import org.example.petstore.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    Department toEntity(DepartmentDto departmentDto);
    @Mapping(source = "department.departmentId", target = "id")
    @Mapping(source = "department.imagePath", target = "imageUrl")
    DepartmentDto toDto(Department department);
}
