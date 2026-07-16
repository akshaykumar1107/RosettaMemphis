package com.rosetta.app.repository;

import com.rosetta.app.entity.ApiKey;
import com.rosetta.app.entity.ApiKeyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, ApiKeyId>
{
    List<ApiKey> findByUser_UserId(Long userId);//find by user.userId of ApiKey, List used where record count can exceed one.
    Optional<ApiKey> findByIdApiKey(String apiKey);//find by id.apiKey of ApiKey, Optional is used where record count cannot exceed one. apiKey is a UK.
}
