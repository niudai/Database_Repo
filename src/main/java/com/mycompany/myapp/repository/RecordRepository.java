package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Record;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Record entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
}
