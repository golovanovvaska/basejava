package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

public class ResumeTestData {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Введите полное имя: ");
        String fullName = scanner.nextLine();
        Resume resume = new Resume(fullName, fillContacts(), fillSections());
        printResume(resume);
    }

    private static Map<Contacts, Link> fillContacts() {
        Map<Contacts, Link> contacts = new EnumMap<>(Contacts.class);
        for (Contacts contact : Contacts.values()) {
            System.out.println("Введите " + contact.name());
            String link = scanner.nextLine();
            contacts.put(Contacts.valueOf(contact.name()), new Link(link, link));
        }
        return contacts;
    }

    private static Map<Sections, Section> fillSections() {
        Map<Sections, Section> sections = new EnumMap<>(Sections.class);
        try {
            System.out.println("Позиция");
            sections.put(Sections.OBJECTIVE, new PersonalAndObjective(scanner.nextLine()));
            System.out.println("Личные качества");
            sections.put(Sections.PERSONAL, new PersonalAndObjective(scanner.nextLine()));
            System.out.println("Достижения");
            sections.put(Sections.ACHIEVEMENT, fillAchievementAndQualification());
            System.out.println("Квалификация");
            sections.put(Sections.QUALIFICATIONS, fillAchievementAndQualification());
            System.out.println("Опыт работы");
            sections.put(Sections.EXPERIENCE, fillExperienceAndEducation(true));
            System.out.println("Образование");
            sections.put(Sections.EDUCATION, fillExperienceAndEducation(false));
        } catch (ParseException e) {

        }
        return sections;
    }

    private static Section fillAchievementAndQualification() {
        AchievementAndQualification o = new AchievementAndQualification();
        String answer;
        while (true) {
            o.addAchievementAndQualification(scanner.nextLine());
            System.out.println("Добавить достижения или квалификацию?(yes/no)");
            answer = scanner.nextLine();
            if (!answer.equals("yes")) {
                break;
            }
        }
        return o;
    }

    private static Section fillExperienceAndEducation(Boolean exp) throws ParseException {
        ExperienceAndEducation o = new ExperienceAndEducation();
        SimpleDateFormat s = new SimpleDateFormat("MM/yyyy");
        String answer;
        while (true) {
            System.out.println("Домашняя страница: ");
            String homePage = scanner.nextLine();
            System.out.println("URL: ");
            String url = scanner.nextLine();
            Link link = new Link(homePage, url);
            System.out.println("Наименование организации: ");
            String organizationName = scanner.nextLine();
            String position = "";
            if (exp) {
                System.out.println("Позиция: ");
                position = scanner.nextLine();
            }
            System.out.println("Достижения: ");
            String achievements = scanner.nextLine();
            System.out.println("Дата начала: ");
            Date startDate = s.parse(scanner.nextLine());
            System.out.println("Дата окончания: ");
            Date endDate = s.parse(scanner.nextLine());
            Organization organization = new Organization(link, organizationName, position,
                    achievements, startDate, endDate);
            o.addExperienceAndEducation(organization);
            System.out.println("Продолжить?(yes/no)");
            answer = scanner.nextLine();
            if (!answer.equals("yes")) {
                break;
            }
        }
        return o;
    }

    private static void printResume(Resume resume) {
        System.out.println(resume.getFullName());
        for (Contacts contact : Contacts.values()) {
            System.out.println(contact.name() + ": " + resume.getContacts().get(contact));
        }
        for (Sections section : Sections.values()) {
            System.out.println(resume.getSections().get(section));
        }
    }
}
