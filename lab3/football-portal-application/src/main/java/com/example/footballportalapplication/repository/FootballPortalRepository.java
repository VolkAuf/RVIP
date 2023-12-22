package com.example.footballportalapplication.repository;

import com.example.footballportalapplication.model.FootballPortal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FootballPortalRepository extends JpaRepository<FootballPortal, Long> {
    Optional<FootballPortal> findByUuid(String uuid);
}
