package com.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;
    private String website;
    private String name;
    private List<Period> periods;

    public Organization() {
    }

    public Organization(String website, String name, List<Period> periods) {
        this.website = website;
        this.name = name;
        this.periods = periods;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    public String getWebsite() {
        return website;
    }

    public String getName() {
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
