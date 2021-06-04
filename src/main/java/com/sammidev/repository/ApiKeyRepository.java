package com.sammidev.repository;

import com.sammidev.entity.ApiKey;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApiKeyRepository extends MongoRepository<ApiKey, String> {
}