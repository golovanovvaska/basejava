package com.basejava.webapp.storage;

import com.basejava.webapp.Config;

public class SqlStorageTest extends AbstractArrayStorageTest {
    private final static String dbUrl = Config.getInstance().getDbUrl();
    private final static String dbUser = Config.getInstance().getDbUser();
    private final static String dbPassword = Config.getInstance().getDbPassword();

    public SqlStorageTest() {
        super(new SqlStorage(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void storageOverflow() {
    }
}