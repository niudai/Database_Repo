package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Interval;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Interval entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntervalRepository extends JpaRepository<Interval, Long> {
}
