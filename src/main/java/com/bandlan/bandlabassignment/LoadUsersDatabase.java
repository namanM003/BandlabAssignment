package com.bandlan.bandlabassignment;

import com.bandlan.bandlabassignment.models.dbentity.UserInfo;
import com.bandlan.bandlabassignment.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadUsersDatabase {

    @Bean
    CommandLineRunner initUsersDatabase(UserRepository userRepository) {
        return args -> {
            userRepository.save(UserInfo.builder()
                    .userDisplayName("naman")
                    .build());
            userRepository.save(UserInfo.builder()
                    .userDisplayName("bandlab")
                    .build());
        };
    }
}
