package com.basejava.webapp.model;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final Map<Contacts, String> contacts;
    private final Map<Sections, Section> sections;

    public Resume(String fullName, Map<Contacts, String> contacts, Map<Sections, Section> sections) {
        this.fullName = fullName;
        this.contacts = new EnumMap<>(contacts);
        this.sections = new EnumMap<>(sections);
        this.uuid = UUID.randomUUID().toString();
    }

    public Resume() {
        this.fullName = "fullName";
        this.contacts = new HashMap<>();
        this.sections = new EnumMap<>(Sections.class);
        this.uuid = UUID.randomUUID().toString();
    }

    public Resume(String uuid) {
        this.fullName = "fullName";
        this.contacts = new HashMap<>();
        this.sections = new EnumMap<>(Sections.class);
        this.uuid = uuid;
    }

    public Resume(String uuid, String fullName) {
        this.fullName = fullName;
        this.contacts = new HashMap<>();
        this.sections = new EnumMap<>(Sections.class);
        this.uuid = uuid;
    }

    public Map<Contacts, String> getContacts() {
        return contacts;
    }

    public Map<Sections, Section> getSections() {
        return sections;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }
}
