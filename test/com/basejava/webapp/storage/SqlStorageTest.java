package com.basejava.webapp.storage;

import com.basejava.webapp.Config;

public class SqlStorageTest extends AbstractArrayStorageTest {
    public SqlStorageTest() {
        super(Config.getInstance().getStorage());
    }

    @Override
    public void storageOverflow() {
    }
}