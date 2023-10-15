package com.basejava.webapp.model;

public enum Sections {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private final String section;

    Sections(String section) {
        this.section = section;
    }

    public String getSection() {
        return section;
    }
}
