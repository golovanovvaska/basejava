package com.basejava.webapp.storage;

public class MapStorageUuidTest extends AbstractArrayStorageTest {

    public MapStorageUuidTest() {
        super(new MapUuidStorage());
    }

    @Override
    public void storageOverflow() {
    }
}