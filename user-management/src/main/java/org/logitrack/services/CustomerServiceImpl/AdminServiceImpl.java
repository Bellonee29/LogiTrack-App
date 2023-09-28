package org.logitrack.services.CustomerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.logitrack.entities.Admin;
import org.logitrack.enums.Role;
import org.logitrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Configuration
@Slf4j
@Service
public class AdminServiceImpl {
    private UserRepository userRepository;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        runAtStart();
    }

    public void runAtStart() {
        log.info("Creating admin");
        Admin admin = new Admin();
        admin.setEmail("AdminOne@gmail.com");
        admin.setPassword("OneAdmin246");
        admin.setRole(Role.ADMIN);
        admin.setCreationDate(LocalDateTime.now());
        admin.setLastLogin(LocalDateTime.now());
        admin.setPhoneNumber("09039156872");
        admin.setFullName("David Black");
        userRepository.save(admin);
    }

    public boolean adminLogin(String email, String password) {
        if ("AdminOne@gmail.com".equals(email) && "OneAdmin246".equals(password)) {
            log.info("Admin login successful");
            return true;
        } else {
            log.info("Admin login failed");
            return false;
        }
    }
}
