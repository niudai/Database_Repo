package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Major;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Major entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
}
