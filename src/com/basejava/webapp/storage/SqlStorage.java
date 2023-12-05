package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.model.Section;
import com.basejava.webapp.model.Sections;
import com.basejava.webapp.sql.SqlHelper;
import com.basejava.webapp.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute(null, "DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(null, connection -> {
            Resume resume;
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, resume);
                }
            }

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, resume);
                }
            }
            return resume;
        });

    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(r, connection -> {
            try (PreparedStatement ps = connection.prepareStatement(
                    "UPDATE resume SET full_name = ?  WHERE uuid= ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteString(r.getUuid(), "DELETE FROM contact WHERE resume_uuid = ?");
            insertContacts(connection, r);
            deleteString(r.getUuid(), "DELETE FROM section WHERE resume_uuid = ?");
            insertSections(connection, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(r, connection -> {
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContacts(connection, r);
            insertSections(connection, r);
            return null;
        });

    }

    @Override
    public void delete(String uuid) {
        deleteString(uuid, "DELETE FROM resume WHERE uuid = ?");
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(null, connection -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume r ORDER BY full_name;")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    String fullName = rs.getString("full_name");
                    resumes.put(uuid, new Resume(uuid, fullName));
                }
            }

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM contact;")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addContact(rs, resume);
                }
            }

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM section;")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addSection(rs, resume);
                }
            }
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute(null, "SELECT COUNT(*) as size FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("size") : 0;
        });
    }

    private void deleteString(String uuid, String sql) {
        sqlHelper.execute(null, sql, ps -> {
            ps.setString(1, uuid);
            ps.execute();
            return null;
        });
    }

    private void insertContacts(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<Sections, Section> e : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, JsonParser.write(e.getValue(), Section.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String type = rs.getString("type");
        if (type != null) {
            ContactType contactType = ContactType.valueOf(type);
            String value = rs.getString("value");
            resume.addContact(contactType, value);
        }
    }

    private void addSection(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            Sections section = Sections.valueOf(rs.getString("type"));
            resume.addSection(section, JsonParser.read(value, Section.class));
        }
    }
}