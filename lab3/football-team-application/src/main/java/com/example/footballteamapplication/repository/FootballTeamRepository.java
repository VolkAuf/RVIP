package com.example.footballteamapplication.repository;

import com.example.footballteamapplication.model.FootballTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballTeamRepository extends JpaRepository<FootballTeam, Long> {
    FootballTeam findByUuid(String uuid);
}
