package com.basejava.webapp.storage;

public class MapStorageResumeTest extends AbstractArrayStorageTest {
    public MapStorageResumeTest() {
        super(new MapStorageResume());
    }

    @Override
    public void storageOverflow() {}
}