package com.basejava.webapp.storage;

public class MapStorageUuidTest extends AbstractArrayStorageTest {

    public MapStorageUuidTest() {
        super(new MapStorageUuid());
    }

    @Override
    public void storageOverflow() {}
}