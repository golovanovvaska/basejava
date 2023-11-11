package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serializer.StreamSerializer;

public class ObjectStreamFileStorageTest extends AbstractArrayStorageTest {
    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new StreamSerializer()) {
        });
    }

    @Override
    public void storageOverflow() {
    }
}