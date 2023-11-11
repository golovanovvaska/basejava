package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serializer.XmlStreamSerializer;

public class XmlStreamPathStorageTest extends AbstractArrayStorageTest {
    public XmlStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamSerializer()));
    }

    @Override
    public void storageOverflow() {
    }
}