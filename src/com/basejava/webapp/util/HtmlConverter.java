package com.basejava.webapp.util;

import com.basejava.webapp.model.*;

public class HtmlConverter {
    public static String contactsToHtml(ContactType type, String contact) {
        contact = contact == null ? "" : contact;
        switch (type) {
            case PHONE_NUMBER -> {
                return toLink("tel:", contact);
            }
            case SKYPE -> {
                return toLink("skype:", contact);
            }
            case MAIL -> {
                return toLink("mailto:", contact);
            }
            case LINKEDIN -> {
                return toLink("", contact, "Профиль LinkedIn");
            }
            case GITHUB -> {
                return toLink("", contact, "Профиль GitHub");
            }
            case STACKOVERFLOW -> {
                return toLink("", contact, "Профиль StackOverflow");
            }
            case HOMEPAGE -> {
                return toLink("", contact, "Домашняя страница");
            }
            default -> {
                return toLink("", contact);
            }
        }
    }


    public static String sectionsToView(Sections section, Section value) {
        if (value != null) {
            switch (section) {
                case OBJECTIVE: {
                    return "<p class = \"position\">" + ((TextSection) value).getText() + "</p>";
                }
                case PERSONAL: {
                    return "<p class = \"qualities\">" + ((TextSection) value).getText() + "</p>";
                }
                case ACHIEVEMENT:
                case QUALIFICATIONS: {
                    StringBuilder sb = new StringBuilder();
                    ((ListSection) value).getList().forEach(text -> {
                        sb.append("<li>");
                        sb.append(text);
                        sb.append("</li>");
                    });
                    return "<ul class = \"list\">" + sb.toString() + "</ul>";
                }
                case EDUCATION:
                case EXPERIENCE: {
                    StringBuilder sb = new StringBuilder();
                    for (Organization organization : ((OrganizationSection) value).getList()) {
                        sb.append("<div class = \"section-wrapper\">");
                        sb.append("<div class = \"job-name\">");
                        sb.append("<a class = \"contact-link\" href = \"" + organization.getWebsite() + "\">"
                                + organization.getName() + "</a>");
                        sb.append("</div>");
                        for (Period period : organization.getPeriods()) {
                            sb.append("<div class = \"period-position\">");
                            sb.append("<div class = \"period\">" + DateUtil.dateToString(period.getStartDate())
                                    + "-" + DateUtil.dateToString(period.getStartDate()) + "</div>");
                            sb.append("<div class = \"position\">" + period.getTitle() + "</div>");
                            sb.append("</div>");
                            if (section.name() == "EXPERIENCE") {
                                sb.append("<div class = \"description\">" + period.getDescription() + "</div>");
                            }
                        }
                        sb.append("</div>");
                    }
                    return sb.toString();
                }
            }
        }
        return "";
    }

    public static String sectionsToEdit(Sections section, Section value) {
        if (value != null) {
            switch (section) {
                case OBJECTIVE:
                case PERSONAL: {
                    return "<textarea class=\"field\" name=\"" + section + "\">" + ((TextSection) value).getText() + "</textarea>";
                }
                case ACHIEVEMENT:
                case QUALIFICATIONS: {
                    return "<textarea class=\"field\" name=\"" + section + "\">" + String.join("\n", ((ListSection) value).getList()) + "</textarea>";
                }
                case EDUCATION:
                case EXPERIENCE: {
                    StringBuilder sb = new StringBuilder();
                    int counter = 0;
                    for (Organization organization : ((OrganizationSection) value).getList()) {
                        sb.append("<input class=\"field\" type=\"text\" placeholder=\"Название\" name=\""
                                + section + "Name\" size=\"100\" value=\"" + organization.getName()
                                + "\">");
                        sb.append("<input class=\"field\" type=\"text\" placeholder=\"Ссылка\" name=\""
                                + section + "Link\" size=\"100\" value=\"" + organization.getWebsite()
                                + "\">");
                        for (Period period : organization.getPeriods()) {
                            sb.append("<div class = \'date-section\'>");
                            sb.append("<input class=\"field date\" name=\"" + section + counter
                                    + "StartDate\" placeholder=\"Начало, ММ/ГГГГ\" size=\"10\" value=\""
                                    + DateUtil.dateToString(period.getStartDate()) + "\">");
                            sb.append("<input class=\"field date date-margin\" name=\"" + section + counter
                                    + "EndDate\" placeholder=\"Окончание, ММ/ГГГГ\" size=\"10\" value=\""
                                    + DateUtil.dateToString(period.getEndDate()) + "\">");
                            sb.append("</div>");
                            sb.append("<input class=\"field\" type=\"text\" placeholder=\"Заголовок\" name=\""
                                    + section + counter + "Title\" size=\"75\" value=\"" + period.getTitle()
                                    + "\">");
                            sb.append("<textarea class=\"field\" placeholder=\"Описание\" name=\"" + section
                                    + counter + "Description\">" + period.getDescription() + "</textarea>");
                        }
                        sb.append("<div class=\"spacer\"></div>");
                        counter++;
                    }
                    return sb.toString();
                }
            }
        }
        return "";
    }

    private static String toLink(String prefix, String contact) {
        return toLink(prefix, contact, contact);
    }

    private static String toLink(String prefix, String contact, String title) {
        switch (prefix) {
            case "tel:" -> {
                return "<li><span>Тел: </span><a class=\"contact-link\" href=" + prefix + contact + ">" + title + "</a></li>";
            }
            case "skype:" -> {
                return "<li><span>Skype: </span><a class=\"contact-link\" href=" + prefix + contact + ">" + title + "</a></li>";
            }
            case "mailto:" -> {
                return "<li><span>Почта: </span><a class=\"contact-link\" href=" + prefix + contact + ">" + title + "</a></li>";
            }
            default -> {
                return "<li><a class=\"contact-link\" href=" + contact + ">" + title + "</a>";
            }
        }
    }
}
