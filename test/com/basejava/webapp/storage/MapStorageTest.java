package com.basejava.webapp.storage;

import org.junit.jupiter.api.Disabled;

class MapStorageTest extends AbstractArrayStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Disabled
    void storageOverflow() {
    }
}