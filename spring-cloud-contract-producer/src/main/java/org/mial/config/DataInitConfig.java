package org.mial.config;

import lombok.extern.slf4j.Slf4j;
import org.mial.dto.UserDTO;
import org.mial.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.stream.IntStream;

@Slf4j
@Profile("dev")
@Configuration
public class DataInitConfig {

    @Value("${app.generate.users:15}")
    private int usersCount;

    @Bean
    CommandLineRunner initDatabase(UserService userService) {
        return args -> {
            IntStream.range(0, usersCount).forEach(i -> userService.addUser(UserDTO.builder()
                .firstName("firstName-" + i)
                .lastName("lastName-" + i)
                .email("email-" + i)
                .build()));

            userService.getUsers().forEach(e -> log.info("Preloaded {}", e));
        };
    }
}
