package com.securetrack.config;

import com.securetrack.entity.*;
import com.securetrack.repository.IncidenceRepository;
import com.securetrack.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, IncidenceRepository incidenceRepository) {
        return args -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            // Crear usuario admin si no existe
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User(
                        "admin",
                        "admin@securetrack.com",
                        encoder.encode("admin123"),
                        "Admin",
                        "System",
                        Role.ADMIN
                );
                userRepository.save(admin);
                System.out.println("✅ Admin user created: admin / admin123");
            }

            // Crear usuario normal si no existe
            if (!userRepository.existsByUsername("user1")) {
                User user1 = new User(
                        "user1",
                        "user1@securetrack.com",
                        encoder.encode("user123"),
                        "John",
                        "Doe",
                        Role.USER
                );
                userRepository.save(user1);
                System.out.println("✅ Test user created: user1 / user123");
            }

            // Crear algunas incidencias de prueba si no hay ninguna
            if (incidenceRepository.count() == 0) {
                User admin = userRepository.findByUsername("admin").orElseThrow();
                User user1 = userRepository.findByUsername("user1").orElseThrow();

                Incidence inc1 = new Incidence(
                        "Server not responding",
                        "The main server is not responding to ping requests since this morning",
                        Priority.CRITICAL
                );
                inc1.setCreatedBy(admin);
                inc1.setAssignedTo(admin);
                inc1.setStatus(IncidenceStatus.OPEN);
                incidenceRepository.save(inc1);

                Incidence inc2 = new Incidence(
                        "Login page error",
                        "Users report a 500 error when trying to log in with valid credentials",
                        Priority.HIGH
                );
                inc2.setCreatedBy(user1);
                inc2.setAssignedTo(user1);
                inc2.setStatus(IncidenceStatus.IN_PROGRESS);
                incidenceRepository.save(inc2);

                Incidence inc3 = new Incidence(
                        "Update documentation",
                        "The API documentation needs to be updated with the new endpoints",
                        Priority.LOW
                );
                inc3.setCreatedBy(admin);
                inc3.setAssignedTo(admin);
                inc3.setStatus(IncidenceStatus.OPEN);
                incidenceRepository.save(inc3);

                Incidence inc4 = new Incidence(
                        "Database backup failure",
                        "The automatic backup scheduled for last night did not run",
                        Priority.HIGH
                );
                inc4.setCreatedBy(user1);
                inc4.setAssignedTo(user1);
                inc4.setStatus(IncidenceStatus.OPEN);
                incidenceRepository.save(inc4);

                Incidence inc5 = new Incidence(
                        "Printer not working",
                        "The office printer on floor 2 is not printing color documents",
                        Priority.MEDIUM
                );
                inc5.setCreatedBy(admin);
                inc5.setAssignedTo(admin);
                inc5.setStatus(IncidenceStatus.RESOLVED);
                incidenceRepository.save(inc5);

                System.out.println("✅ 5 sample incidences created");
            }
        };
    }
}