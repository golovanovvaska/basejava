package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        if (uuid == null || uuid.length() == 0) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (check(value)) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (Sections section : Sections.values()) {
            String value = request.getParameter(section.name()) != null ? request.getParameter(section.name()) :
                    request.getParameter(section.name() + "Name");
            if (check(value)) {
                r.getSections().remove(section);
            } else {
                switch (section) {
                    case OBJECTIVE:
                    case PERSONAL: {
                        r.addSection(section, new TextSection(value));
                        break;
                    }
                    case ACHIEVEMENT:
                    case QUALIFICATIONS: {
                        r.addSection(section, new ListSection(Arrays.asList(value.split("\\r\\n"))));
                        break;
                    }
                    case EXPERIENCE:
                    case EDUCATION: {
                        OrganizationSection organizationSection = addOrganizationSection(request, section);
                        r.addSection(section, organizationSection);
                        break;
                    }
                }

            }
        }
        if (uuid == null || uuid.length() == 0) {
            storage.save(r);
        } else {
            storage.update(r);

        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "add":
                r = new Resume();
                break;
            case "edit":
                r = storage.get(uuid);
                for (Sections type : Sections.values()) {
                    Section section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = new TextSection("");
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = new ListSection(new ArrayList<>());
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationSection organizationSection = (OrganizationSection) section;
                            List<Organization> emptyOrganizations = new ArrayList<>();
                            emptyOrganizations.add(new Organization("", ""
                                    , Arrays.asList(new Period("", "", null, null))));
                            if (organizationSection != null) {
                                for (Organization org : organizationSection.getList()) {
                                    List<Period> emptyPeriods = new ArrayList<>();
                                    emptyPeriods.addAll(org.getPeriods());
                                    emptyOrganizations.add(new Organization(org.getWebsite(), org.getName(), emptyPeriods));
                                }
                            }
                            section = new OrganizationSection(emptyOrganizations);
                            break;
                    }
                    r.addSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);

    }

    private OrganizationSection addOrganizationSection(HttpServletRequest request, Sections section) {
        List<Organization> organizations = new ArrayList<>();
        String[] organizationNames = request.getParameterValues(section.name() + "Name");
        String[] organizationLink = request.getParameterValues(section.name() + "Link");
        for (int i = 0; i < organizationNames.length; i++) {
            if (!organizationNames[i].isEmpty()) {
                String[] titles = request.getParameterValues(section.name() + i + "Title");
                String[] descriptions = request.getParameterValues(section.name() + i + "Description");
                String[] startDates = request.getParameterValues(section.name() + i + "StartDate");
                String[] endDates = request.getParameterValues(section.name() + i + "EndDate");
                List<Period> periods = addPeriods(titles, descriptions, startDates, endDates);
                organizations.add(new Organization(organizationLink[i], organizationNames[i], periods));
            }
        }
        return new OrganizationSection(organizations);
    }

    private List<Period> addPeriods(String[] titles, String[] descriptions, String[] startDates, String[] endDates) {
        List<Period> periods = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            if (check(startDates[i]) && check(endDates[i])) {
                periods.add(new Period(titles[i], descriptions[i]
                        , startDates[i].isEmpty() ? null : localDateParser(startDates[i])
                        , endDates[i].isEmpty() ? null : localDateParser(endDates[i])));
            }
        }
        return periods;
    }

    private LocalDate localDateParser(String date) {
        return YearMonth.parse(date, DateTimeFormatter.ofPattern("MM/yyyy")).atDay(1);
    }

    private boolean check(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
