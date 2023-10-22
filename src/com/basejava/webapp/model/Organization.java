package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final String website;
    private final String name;
    private final List<Period> periods = new ArrayList<>();

    public Organization(String website, String name) {
        this.website = website;
        this.name = name;
    }

    public void addPeriod(String title, String description, LocalDate startDate, LocalDate endDate) {
        Period period = new Period(title, description, startDate, endDate);
        periods.add(period);
    }

    public String getWebsite() {
        return website;
    }

    public String getname() {
        return name;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!Objects.equals(website, that.website)) return false;
        if (!name.equals(that.name)) return false;
        return periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        int result = website != null ? website.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "\n" + name + "\n" + periodsToString();
    }

    private String periodsToString() {
        StringBuilder str = new StringBuilder();
        for (Period period : periods) {
            str.append(period.toString());
        }
        return str.toString();
    }
}
