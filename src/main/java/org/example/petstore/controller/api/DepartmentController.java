package org.example.petstore.controller.api;

import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.department.DepartmentDto;
import org.example.petstore.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    /**
     * Retrieves a list of all product departments.
     *
     * @return a ResponseEntity containing a list of DepartmentDto objects representing the categories
     */
    @GetMapping()
    public ResponseEntity<List<DepartmentDto>> getCategories() {
        List<DepartmentDto> departments = departmentService.findAllDepartments();
        return ResponseEntity.ok(departments);
    }
}
