package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.time.LocalDate;

public class ResumeTestData {

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        fillContacts(resume);
        fillSections(resume);
        return resume;
    }

    private static void fillContacts(Resume resume) {
        resume.addContact(ContactType.PHONE_NUMBER, "+7(921) 855-0482)");
        resume.addContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.addContact(ContactType.MAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.addContact(ContactType.HOMEPAGE, "http://gkislin.ru/");
    }

    private static void fillSections(Resume resume) {
        fillTextSections(resume);
        fillListSections(resume);
        fillOrganizationSections(resume);
    }

    private static void fillTextSections(Resume resume) {
        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения " +
                "по Java Web и Enterprise технологиям");
        resume.addSection(Sections.OBJECTIVE, objective);

        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, " +
                "инициативность. Пурист кода и архитектуры.");
        resume.addSection(Sections.PERSONAL, personal);
    }

    private static void fillListSections(Resume resume) {
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
        resume.addSection(Sections.ACHIEVEMENT, achievement);

        ListSection qualifications = new ListSection();
        qualifications.addText("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.addText("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.addText("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, " +
                "SQLite, MS SQL, HSQLDB");
        qualifications.addText("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualifications.addText("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        resume.addSection(Sections.QUALIFICATIONS, qualifications);
    }

    private static void fillOrganizationSections(Resume resume) {
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
        resume.addSection(Sections.EXPERIENCE, experience);

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
        resume.addSection(Sections.EDUCATION, education);
    }
}
