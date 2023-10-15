package com.basejava.webapp.model;

public class PersonalAndObjective extends Section {

    private final String text;

    public PersonalAndObjective(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
