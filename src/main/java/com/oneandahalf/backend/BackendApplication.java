package com.oneandahalf.backend;

import com.oneandahalf.backend.admin.auth.domain.Admin;
import com.oneandahalf.backend.admin.auth.domain.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

@ConfigurationPropertiesScan
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Component
    @RequiredArgsConstructor
    static class AdminData implements CommandLineRunner {

        private final AdminRepository adminRepository;

        @Override
        public void run(String... args) {
            adminRepository.save(new Admin("admin", "admin"));
        }
    }
}
