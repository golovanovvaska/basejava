package com.basejava.webapp.sql;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(Resume resume, String sqlQuery) {
        execute(resume, sqlQuery, PreparedStatement::execute);
    }

    public <T> T execute(Resume resume, String sqlQuery, Helper<T> helper) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            return helper.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(resume.getUuid());
            } else {
                throw new StorageException(e);
            }
        }
    }

    public interface Helper<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }
}
