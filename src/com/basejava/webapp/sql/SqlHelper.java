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
            throw defineException(e, resume);
        }
    }

    public <T> T transactionalExecute(Resume resume, SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw defineException(e, resume);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    private StorageException defineException(SQLException e, Resume r) {
        if (e.getSQLState().equals("23505")) {
            return new ExistStorageException(r.getUuid());
        } else {
            return new StorageException(e);
        }
    }

    public interface Helper<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

    public interface SqlTransaction<T> {
        T execute(Connection connection) throws SQLException;
    }
}
