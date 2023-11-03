package com.basejava.webapp.storage;

import com.basejava.webapp.storage.strategies.StreamStrategy;

public class ObjectStreamFileStorageTest extends AbstractArrayStorageTest {
    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new StreamStrategy()) {
        });
    }

    @Override
    public void storageOverflow() {
    }
}