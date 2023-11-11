
package com.basejava.webapp.util;


import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class XmlLocalDateAdapter extends XmlAdapter<String, LocalDate> {


    @Override
    public String marshal(LocalDate date) {
        return date.toString();
    }

    @Override
    public LocalDate unmarshal(String date) {
        return LocalDate.parse(date);
    }
}