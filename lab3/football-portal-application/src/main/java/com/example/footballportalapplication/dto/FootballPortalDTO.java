package com.example.footballportalapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FootballPortalDTO {
    private String name;
    private String supportedPlatforms;
    private int maximumUsers;
}
