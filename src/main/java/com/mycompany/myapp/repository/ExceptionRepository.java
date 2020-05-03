package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Exception;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Exception entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExceptionRepository extends JpaRepository<Exception, Long> {
}
