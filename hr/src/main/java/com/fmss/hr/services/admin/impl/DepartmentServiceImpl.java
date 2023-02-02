package com.fmss.hr.services.admin.impl;

import com.fmss.hr.common.constant.GenericMessages;
import com.fmss.hr.entities.Department;
import com.fmss.hr.exceptions.CustomException;
import com.fmss.hr.repos.admin.DepartmentRepository;
import com.fmss.hr.services.admin.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getById(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new CustomException(GenericMessages.DEPARTMENT_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public void create(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public Department update(Long departmentId, Department newDepartment) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if(department.isPresent()){
            Department foundDepartment = department.get();
            foundDepartment.setName(foundDepartment.getName());
            foundDepartment.setUser(foundDepartment.getUser());
            departmentRepository.save(foundDepartment);
            return foundDepartment;
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }
}
