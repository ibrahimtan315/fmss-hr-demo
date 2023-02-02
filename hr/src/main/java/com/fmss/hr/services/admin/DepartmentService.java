package com.fmss.hr.services.admin;

import com.fmss.hr.entities.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getAll();
    Department getById(Long id);
    void create(Department department);
    Department update(Long userId,Department newDepartment);
    void deleteById(Long id);
}
