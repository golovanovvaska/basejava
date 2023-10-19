package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин", fillContacts(), fillSections());
        printResume(resume);
    }

    private static Map<Contacts, String> fillContacts() {
        Map<Contacts, String> contacts = new HashMap<>();
        contacts.put(Contacts.PHONE_NUMBER, "+7(921) 855-0482)");
        contacts.put(Contacts.SKYPE, "skype:grigory.kislin");
        contacts.put(Contacts.MAIL, "gkislin@yandex.ru");
        contacts.put(Contacts.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contacts.put(Contacts.GITHUB, "https://github.com/gkislin");
        contacts.put(Contacts.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        contacts.put(Contacts.HOMEPAGE, "http://gkislin.ru/");
        return contacts;
    }

    private static Map<Sections, Section> fillSections() {
        Map<Sections, Section> sections = new HashMap<>();
        fillTextSections(sections);
        fillListSections(sections);
        fillOrganizationSections(sections);
        return sections;
    }

    private static void fillTextSections(Map<Sections, Section> sections) {
        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения " +
                "по Java Web и Enterprise технологиям");
        sections.put(Sections.OBJECTIVE, objective);

        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, " +
                "инициативность. Пурист кода и архитектуры.");
        sections.put(Sections.PERSONAL, personal);
    }

    private static void fillListSections(Map<Sections, Section> sections) {
        ListSection achievement = new ListSection();
        achievement.addText("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов " +
                "на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для " +
                "комплексных DIY смет");
        achievement.addText("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 3500 выпускников.");
        achievement.addText("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike." +
                " Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        sections.put(Sections.ACHIEVEMENT, achievement);

        ListSection qualifications = new ListSection();
        qualifications.addText("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.addText("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.addText("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, " +
                "SQLite, MS SQL, HSQLDB");
        qualifications.addText("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualifications.addText("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        sections.put(Sections.QUALIFICATIONS, qualifications);
    }

    private static void fillOrganizationSections(Map<Sections, Section> sections) {
        OrganizationSection experience = new OrganizationSection();
        Organization organization = new Organization("http://javaops.ru/", "Java Online Projects");
        organization.addPeriod("Автор проекта.", "Создание, организация и проведение Java онлайн" +
                " проектов и стажировок.", LocalDate.of(2013, 10, 1), LocalDate.now());
        experience.addOrganization(organization);
        organization = new Organization("https://www.wrike.com/", "Wrike");
        organization.addPeriod("Старший разработчик (backend)", "Проектирование и разработка онлайн" +
                        " платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL," +
                        " Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1));
        experience.addOrganization(organization);
        organization = new Organization("", "RIT Center");
        organization.addPeriod("Java архитектор", "Организация процесса разработки системы ERP для " +
                        "разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы" +
                        " (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД" +
                        " и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                        "сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online" +
                        " редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons," +
                        " Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, " +
                        "Unix shell remote scripting via ssh tunnels, PL/Python", LocalDate.of(2012, 4, 1),
                LocalDate.of(2014, 10, 1));
        experience.addOrganization(organization);
        sections.put(Sections.EXPERIENCE, experience);

        OrganizationSection education = new OrganizationSection();
        organization = new Organization("http://www.siemens.ru/", "Siemens AG");
        organization.addPeriod("", "3 месяца обучения мобильным IN сетям (Берлин)",
                LocalDate.of(2005, 1, 1), LocalDate.of(2005, 4, 1));
        education.addOrganization(organization);
        organization = new Organization("http://www.alcatel.ru/", "Alcatel");
        organization.addPeriod("", "6 месяцев обучения цифровым телефонным сетям (Москва)",
                LocalDate.of(1997, 9, 1), LocalDate.of(1998, 3, 1));
        education.addOrganization(organization);
        organization = new Organization("http://www.ifmo.ru/", "Санкт-Петербургский национальный" +
                " исследовательский университет информационных технологий, механики и оптики");
        organization.addPeriod("", "Аспирантура (программист С, С++)",
                LocalDate.of(1993, 9, 1), LocalDate.of(1996, 7, 1));
        organization.addPeriod("", "Инженер (программист Fortran, C)",
                LocalDate.of(1987, 9, 1), LocalDate.of(1993, 7, 1));
        education.addOrganization(organization);
        sections.put(Sections.EDUCATION, education);
    }

    private static void printResume(Resume resume) {
        System.out.println(resume.getFullName());
        printContacts(resume);
        printSections(resume);
    }

    private static void printContacts(Resume resume) {
        Map<Contacts, String> contacts = resume.getContacts();
        System.out.println();
        for (Contacts contact : contacts.keySet()) {
            System.out.println(contact.getContact() + ": " + contacts.get(contact));
        }
    }

    private static void printSections(Resume resume) {
        Map<Sections, Section> sections = resume.getSections();
        for (Sections section : sections.keySet()) {
            System.out.println("\n" + section.getSection());
            System.out.println(sections.get(section));
        }
    }
}
