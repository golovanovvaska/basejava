package com.basejava.webapp.sql;

import com.basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String sqlQuery) {
        execute(sqlQuery, PreparedStatement::execute);
    }

    public <T> T execute(String sqlQuery, Helper<T> helper) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            return helper.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface Helper<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }
}
