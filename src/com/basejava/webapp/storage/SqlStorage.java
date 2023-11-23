package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.ConnectionFactory;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }


    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });

    }

    @Override
    public void update(Resume r) {
        if (checkAvailability(r.getUuid())) {
            sqlHelper.execute("UPDATE resume SET uuid = ?, full_name = ? WHERE uuid= ?", ps -> {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.setString(3, r.getUuid());
                ps.execute();
                return null;
            });
        } else {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public void save(Resume r) {
        if (!checkAvailability(r.getUuid())) {
            sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
                return null;
            });
        } else {
            throw new ExistStorageException(r.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        if (checkAvailability(uuid)) {
            sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
                ps.setString(1, uuid);
                ps.execute();
                return null;
            });
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume ORDER BY uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                String uuid = rs.getString("uuid").trim();
                String fullName = rs.getString("full_name");
                Resume resume = new Resume(uuid, fullName);
                resumes.add(resume);
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) as size FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            int size = 0;
            while (rs.next()) {
                size = rs.getInt("size");
            }
            return size;
        });
    }

    private boolean checkAvailability(String uuid) {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) != 0;
        });
    }
}