package com.basejava.webapp.storage;

import org.junit.jupiter.api.Disabled;

class ListStorageTest extends AbstractArrayStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
    }
    @Disabled
    void storageOverflow() {}
}