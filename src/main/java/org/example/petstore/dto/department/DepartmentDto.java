package org.example.petstore.dto.department;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentDto {
    private Long id;
    private String name;
    private String imageUrl;
}