package com.basejava.webapp.model;

import java.util.Date;

public class Organization {
    private final Link homePage;
    private final String organizationName;
    private final String position;
    private final String achievements;
    private final Date startDate;
    private final Date endDate;

    public Organization(Link homePage, String organizationName, String position, String achievements,
                        Date startDate, Date endDate) {
        this.homePage = homePage;
        this.organizationName = organizationName;
        this.position = position;
        this.achievements = achievements;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", organizationName='" + organizationName + '\'' +
                ", position='" + position + '\'' +
                ", achievements='" + achievements + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
