package com.basejava.webapp.storage;

public class MapStorageResumeTest extends AbstractArrayStorageTest {
    public MapStorageResumeTest() {
        super(new MapResumeStorage());
    }

    @Override
    public void storageOverflow() {}
}