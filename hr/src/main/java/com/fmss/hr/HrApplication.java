package com.fmss.hr;

import com.fmss.hr.entities.Department;
import com.fmss.hr.entities.Role;
import com.fmss.hr.entities.User;
import com.fmss.hr.repos.admin.DepartmentRepository;
import com.fmss.hr.repos.user.RoleRepository;
import com.fmss.hr.repos.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@SpringBootApplication
@EnableScheduling
public class HrApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
    }

    @Bean
    CommandLineRunner createInitialFields(PasswordEncoder passwordEncoder, RoleRepository roleRepository, DepartmentRepository departmentRepository, UserRepository userRepository) {
        return args -> {

            if(roleRepository.findByName("admin").isPresent())
                return;

            Role admin = new Role();
            admin.setName("admin");
            roleRepository.save(admin);

            if(roleRepository.findByName("user").isPresent())
                return;

            Role user = new Role();
            user.setName("user");
            roleRepository.save(user);

            User adminUser = new User();
            adminUser.setFirstName("admin");
            adminUser.setLastName("admin");
            adminUser.setRole(admin);
            adminUser.setFullName(adminUser.getFirstName() + adminUser.getLastName());
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.setEmail("admin");
            adminUser.setStatus(true);

            userRepository.save(adminUser);

            Department department1 = new Department();
            department1.setName("Yönetim");
            department1.setUser(adminUser);

            departmentRepository.save(department1);
        };
    }
}
