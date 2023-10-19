package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends Section {
    private final List<Organization> list = new ArrayList<>();

    public void addOrganization(Organization organization) {
        this.list.add(organization);
    }

    public List<Organization> getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
