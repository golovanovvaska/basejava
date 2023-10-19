package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends Section {

    private final List<String> list = new ArrayList<>();

    public void addText(String text) {
        this.list.add(text);
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (String text : list) {
            str.append("* ").append(text).append("\n");
        }
        return str.toString();
    }
}
