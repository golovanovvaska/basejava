package com.basejava.webapp.storage;

import com.basejava.webapp.storage.strategies.StreamStrategy;

public class ObjectStreamPathStorageTest extends AbstractArrayStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new StreamStrategy()));
    }

    @Override
    public void storageOverflow() {
    }
}