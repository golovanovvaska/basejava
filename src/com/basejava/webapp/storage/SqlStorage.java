package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
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
        return sqlHelper.execute(null,
                "   SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "       ON r.uuid = c.resume_uuid " +
                        "    WHERE r.uuid =?", ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(rs, resume);
                    } while (rs.next());
                    return resume;
                });

    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(r, connection -> {
            try (PreparedStatement ps = connection.prepareStatement(
                    "UPDATE resume " +
                            "   SET full_name = ? " +
                            " WHERE uuid= ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteString(r.getUuid(), "DELETE FROM contact WHERE resume_uuid = ?");
            insertContacts(connection, r);
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
            return null;
        });

    }

    @Override
    public void delete(String uuid) {
        deleteString(uuid, "DELETE FROM resume WHERE uuid = ?");
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute(null, "SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid ORDER BY full_name;", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String fullName = rs.getString("full_name");
                Resume resume = findResume(resumes, uuid);
                if (resume == null) {
                    resume = new Resume(uuid, fullName);
                    resumes.add(resume);
                }
                addContact(rs, resume);
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute(null, "SELECT COUNT(*) as size FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("size") : 0;
        });
    }

    private Resume findResume(List<Resume> resumes, String uuid) {
        for (Resume r : resumes) {
            if (r.getUuid().equals(uuid)) return r;
        }
        return null;
    }

    private void deleteString(String uuid, String sql) {
        sqlHelper.execute(null, sql, ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
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

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        ContactType contactType = ContactType.valueOf(rs.getString("type"));
        String value = rs.getString("value");
        resume.addContact(contactType, value);
    }
}