package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serializer.JsonStreamSerializer;

public class JsonStreamPathStorageTest extends AbstractArrayStorageTest {
    public JsonStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamSerializer()));
    }

    @Override
    public void storageOverflow() {
    }
}