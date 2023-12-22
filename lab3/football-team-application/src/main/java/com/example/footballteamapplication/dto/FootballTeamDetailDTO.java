package com.example.footballteamapplication.dto;

import lombok.Data;

@Data
public class FootballTeamDetailDTO {
    private String uuid;
    private String name;
    private int establishedYear;
    private String division;
    private String coach;
    private String portalUuid;
    private FootballTeamDetailInfoDTO portalInfo;
}

