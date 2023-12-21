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
                        if (!text.isEmpty()) {
                            sb.append("<li>");
                            sb.append(text);
                            sb.append("</li>");
                        }
                    });
                    return "<ul class = \"list\">" + sb + "</ul>";
                }
                case EDUCATION:
                case EXPERIENCE: {
                    StringBuilder sb = new StringBuilder();
                    for (Organization organization : ((OrganizationSection) value).getList()) {
                        sb.append("<div class = \"section-wrapper\">");
                        sb.append("<div class = \"job-name\">");
                        sb.append("<a class = \"contact-link\" href = \"").append(organization.getWebsite()).append("\">").append(organization.getName()).append("</a>");
                        sb.append("</div>");
                        for (Period period : organization.getPeriods()) {
                            sb.append("<div class = \"period-position\">");
                            sb.append("<div class = \"period\">").append(DateUtil.dateToString(period.getStartDate())).append("-").append(DateUtil.dateToString(period.getStartDate())).append("</div>");
                            sb.append("<div class = \"position\">").append(period.getTitle()).append("</div>");
                            sb.append("</div>");
                            if (section.name().equals("EXPERIENCE")) {
                                sb.append("<div class = \"description\">").append(period.getDescription()).append("</div>");
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
                        sb.append("<input class=\"field\" type=\"text\" placeholder=\"Название\" name=\"").append(section.name()).append("\" size=\"100\" value=\"").append(organization.getName()).append("\">");
                        sb.append("<input class=\"field\" type=\"text\" placeholder=\"Ссылка\" name=\"").append(section).append("Link\" size=\"100\" value=\"").append(organization.getWebsite()).append("\">");
                        for (Period period : organization.getPeriods()) {
                            sb.append("<div class = \"date-section\">");
                            sb.append("<input class=\"field date\" name=\"").append(section).append(counter).append("StartDate\" placeholder=\"Начало, ММ/ГГГГ\" size=\"10\" value=\"").append(DateUtil.dateToString(period.getStartDate())).append("\">");
                            sb.append("<input class=\"field date date-margin\" name=\"").append(section).append(counter).append("EndDate\" placeholder=\"Окончание, ММ/ГГГГ\" size=\"10\" value=\"").append(DateUtil.dateToString(period.getEndDate())).append("\">");
                            sb.append("</div>");
                            sb.append("<input class=\"field\" type=\"text\" placeholder=\"Заголовок\" name=\"").append(section).append(counter).append("Title\" size=\"75\" value=\"").append(period.getTitle()).append("\">");
                                sb.append("<textarea class=\"field\" placeholder=\"Описание\" name=\"").append(section).append(counter).append("Description\">").append(period.getDescription()).append("</textarea>");
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