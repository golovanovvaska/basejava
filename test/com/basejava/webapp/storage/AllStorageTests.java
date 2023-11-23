package com.basejava.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapStorageResumeTest.class,
        MapStorageUuidTest.class,
        ObjectStreamFileStorageTest.class,
        ObjectStreamPathStorageTest.class,
        XmlStreamPathStorageTest.class,
        JsonStreamPathStorageTest.class,
        DataStreamPathStorageTest.class,
        SqlStorageTest.class
})

public class AllStorageTests {
}
