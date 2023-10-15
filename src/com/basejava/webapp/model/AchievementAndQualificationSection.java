package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class AchievementAndQualificationSection extends Section {

    private final List<String> list = new ArrayList<>();

    public void addAchievementAndQualification(String text) {
        this.list.add(text);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
