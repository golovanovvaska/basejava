package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializer {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            dos.writeInt(resume.getContacts().size());
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            dos.writeInt(resume.getSections().size());
            for (Map.Entry<Sections, Section> entry : resume.getSections().entrySet()) {
                Sections section = entry.getKey();
                dos.writeUTF(section.name());
                switch (section) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(entry.getKey().name());
                        dos.writeUTF(entry.getValue().toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeList(dos, entry);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeOrganization(dos, entry);
                        break;
                }
            }
        }
    }

    private void writeList(DataOutputStream dos, Map.Entry<Sections, Section> entry) throws IOException {
        ListSection listSection = (ListSection) entry.getValue();
        List<String> list = listSection.getList();
        dos.writeUTF(entry.getKey().name());
        dos.writeInt(list.size());
        for (String s : list) {
            dos.writeUTF(s);
        }
    }

    private void writeOrganization(DataOutputStream dos, Map.Entry<Sections, Section> entry) throws IOException {
        OrganizationSection organizationSection = (OrganizationSection) entry.getValue();
        List<Organization> organizationList = organizationSection.getList();
        dos.writeUTF(entry.getKey().name());
        dos.writeInt(organizationList.size());
        for (Organization organization : organizationList) {
            dos.writeUTF(organization.getName());
            dos.writeUTF(organization.getWebsite());
            List<Period> periodList = organization.getPeriods();
            dos.writeInt(periodList.size());
            for (Period period : periodList) {
                dos.writeUTF(period.getTitle());
                dos.writeUTF(period.getDescription());
                dos.writeUTF(period.getStartDate().toString());
                dos.writeUTF(period.getEndDate().toString());
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                Sections section = Sections.valueOf(dis.readUTF());
                switch (section) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(Sections.valueOf(dis.readUTF()), new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(Sections.valueOf(dis.readUTF()), readList(dis));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.addSection(Sections.valueOf(dis.readUTF()), readOrganization(dis));
                        break;
                }
            }

            return resume;
        }
    }

    private ListSection readList(DataInputStream dis) throws IOException {
        int listSize = dis.readInt();
        List<String> text = new ArrayList<>();
        for (int j = 0; j < listSize; j++) {
            text.add(dis.readUTF());
        }
        return new ListSection(text);
    }

    private OrganizationSection readOrganization(DataInputStream dis) throws IOException {
        int organizationListSize = dis.readInt();
        List<Organization> organizations = new ArrayList<>();
        for (int j = 0; j < organizationListSize; j++) {
            Organization organization = new Organization();
            organization.setName(dis.readUTF());
            organization.setWebsite(dis.readUTF());
            int periodListSize = dis.readInt();
            List<Period> periods = new ArrayList<>();
            for (int k = 0; k < periodListSize; k++) {
                Period period = new Period();
                period.setTitle(dis.readUTF());
                period.setDescription(dis.readUTF());
                period.setStartDate(LocalDate.parse(dis.readUTF()));
                period.setEndDate(LocalDate.parse(dis.readUTF()));
                periods.add(period);
            }
            organization.setPeriods(periods);
            organizations.add(organization);
        }
        return new OrganizationSection(organizations);
    }
}
