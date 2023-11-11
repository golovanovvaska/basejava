package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serializer.StreamSerializer;

public class ObjectStreamPathStorageTest extends AbstractArrayStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new StreamSerializer()));
    }

    @Override
    public void storageOverflow() {
    }
}