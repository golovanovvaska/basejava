package com.basejava.webapp.model;

public class PersonalAndObjectiveSection extends Section {

    private final String text;

    public PersonalAndObjectiveSection(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
