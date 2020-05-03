package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Campus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Campus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampusRepository extends JpaRepository<Campus, Long> {
}
