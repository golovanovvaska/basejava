package com.basejava.webapp.model;

public enum ContactType {

    PHONE_NUMBER("Тел.:"),
    SKYPE("Skype:"),
    MAIL("Почта:"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль StackOveflow"),
    HOMEPAGE("Домашняя страница");

    private final String contact;

    ContactType(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }
}
