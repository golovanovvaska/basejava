package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeCollection(resume.getContacts().entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            writeCollection(resume.getSections().entrySet(), dos, entry -> {
                Sections section = entry.getKey();
                dos.writeUTF(section.name());
                switch (section) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) entry.getValue()).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(((ListSection) entry.getValue()).getList(), dos, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeOrganization(dos, entry);
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readCollection(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readCollection(dis, () -> {
                Sections section = Sections.valueOf(dis.readUTF());
                switch (section) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(section, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(section, readList(dis));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.addSection(section, readOrganization(dis));
                        break;
                }
            });
            return resume;
        }
    }

    private void writeOrganization(DataOutputStream dos, Map.Entry<Sections, Section> entry) throws IOException {
        OrganizationSection organizationSection = (OrganizationSection) entry.getValue();
        List<Organization> organizationList = organizationSection.getList();
        writeCollection(organizationList, dos, organization -> {
            dos.writeUTF(organization.getWebsite());
            dos.writeUTF(organization.getName());
            List<Period> periodList = organization.getPeriods();
            writeCollection(periodList, dos, period -> {
                dos.writeUTF(period.getTitle());
                dos.writeUTF(period.getDescription());
                dos.writeUTF(period.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
                dos.writeUTF(period.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
            });
        });
    }

    private <T> void writeCollection(Collection<T> collection, DataOutputStream dos, Writer<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.write(element);
        }
    }

    private OrganizationSection readOrganization(DataInputStream dis) throws IOException {
        return new OrganizationSection(readAndFillList(dis, () ->
                new Organization(dis.readUTF(), dis.readUTF(), readAndFillList(dis, () ->
                        new Period(dis.readUTF(), dis.readUTF(), LocalDate.parse(dis.readUTF()),
                                LocalDate.parse(dis.readUTF()))))));
    }

    private void readCollection(DataInputStream dis, VoidReader reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    private <T> List<T> readAndFillList(DataInputStream dis, Reader<T> reader) throws IOException {
        List<T> list = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private ListSection readList(DataInputStream dis) throws IOException {
        return new ListSection(readAndFillList(dis, dis::readUTF));
    }

    private interface Writer<T> {
        void write(T element) throws IOException;

    }

    private interface Reader<T> {
        T read() throws IOException;
    }

    private interface VoidReader {
        void read() throws IOException;
    }

}
