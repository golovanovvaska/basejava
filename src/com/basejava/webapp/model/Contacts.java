package com.basejava.webapp.model;

public enum Contacts {

    PHONE_NUMBER("Тел.:"),
    SKYPE("Skype:"),
    MAIL("Почта:"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль StackOveflow"),
    HOMEPAGE("Домашняя страница");

    private String contact;

    Contacts(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }
}
