package com.rosetta.app.repository;

import com.rosetta.app.entity.TranslationCache;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface TranslationCacheRepository extends CassandraRepository<TranslationCache, String>
{
}
