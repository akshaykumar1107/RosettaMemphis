package com.rosetta.app.repository;

import com.rosetta.app.entity.TranslationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranslationHistoryRepository extends JpaRepository<TranslationHistory, Long>
{
    List<TranslationHistory> findByUser_UserIdOrderByCreatedAtDesc(Long userId);
}
