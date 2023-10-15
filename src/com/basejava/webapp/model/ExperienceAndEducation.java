package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ExperienceAndEducation extends Section {
    private final List<Organization> list = new ArrayList<>();

    public void addExperienceAndEducation(Organization organization) {
        this.list.add(organization);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
