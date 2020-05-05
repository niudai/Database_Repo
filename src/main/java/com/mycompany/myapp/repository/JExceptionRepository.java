package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.JException;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the JException entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JExceptionRepository extends JpaRepository<JException, Long> {
}
