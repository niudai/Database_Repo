package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Semaster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Semaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SemasterRepository extends JpaRepository<Semaster, Long> {
}
