package com.fmss.hr.controllers.admin;

import com.fmss.hr.common.ApiResponse;
import com.fmss.hr.common.constant.GenericMessages;
import com.fmss.hr.dto.DepartmentDto;
import com.fmss.hr.entities.Department;
import com.fmss.hr.entities.User;
import com.fmss.hr.services.admin.DepartmentService;
import com.fmss.hr.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Department>> getAll(){
        List<Department> departments = departmentService.getAll();
        if(!departments.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(departments);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("{departmentId}")
    public ResponseEntity<Department> getById(@PathVariable Long departmentId){
        Department department = departmentService.getById(departmentId);
        return ResponseEntity.ok(department);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody DepartmentDto departmentDto){
        Department department = new Department();
        User user = userService.getUserById(departmentDto.getManagerId());
        if(user != null){
            department.setUser(user);
        }
        department.setName(departmentDto.getName());
        departmentService.create(department);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("{departmentId}")
    public ResponseEntity<?> update(@PathVariable Long departmentId,@RequestBody Department newDepartment){
        Department department = departmentService.update(departmentId,newDepartment);
        return ResponseEntity.ok(department);
    }

    @DeleteMapping("{departmentId}")
    public ResponseEntity<?> delete(@PathVariable Long departmentId){
        departmentService.deleteById(departmentId);
        return ResponseEntity.ok().body(null);
    }

}
