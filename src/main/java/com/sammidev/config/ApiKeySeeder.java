package com.sammidev.config;

import com.sammidev.entity.ApiKey;
import com.sammidev.repository.ApiKeyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApiKeySeeder implements ApplicationRunner {

    private ApiKeyRepository apiKeyRepository;

    @Autowired
    public ApiKeySeeder(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String apiKey = "SECRET";
        if (!apiKeyRepository.existsById(apiKey)) {
            var entity = new ApiKey(apiKey);
            apiKeyRepository.insert(entity);
        }
    }
}