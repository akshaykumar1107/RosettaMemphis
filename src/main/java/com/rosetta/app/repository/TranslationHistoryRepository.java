package com.rosetta.app.repository;

import com.rosetta.app.entity.TranslationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TranslationHistoryRepository extends JpaRepository<TranslationHistory, Long>
{
    List<TranslationHistory> findByUser_UserIdOrderByCreatedAtDesc(Long userId);
}
