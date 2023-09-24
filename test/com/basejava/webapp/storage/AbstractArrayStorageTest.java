package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

abstract class AbstractArrayStorageTest {

    Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(new Resume("uuid1"));
        storage.save(new Resume("uuid2"));
        storage.save(new Resume("uuid3"));
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void update() {
        Resume r = new Resume("uuid2");
        storage.update(r);
    }

    @Test
    void save() {
        Resume r = new Resume("uuid4");
        storage.save(r);
    }

    @Test
    void get() {
        assertEquals("uuid2", storage.get("uuid2").getUuid());
    }

    @Test
    void delete() {
        storage.delete("uuid3");
        assertEquals(2, storage.size());
    }

    @Test
    void getAll() {
        assertEquals(storage.size(), storage.getAll().length);
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
    }

    @Test
    void StorageOverflow() throws NoSuchFieldException, IllegalAccessException {
        storage.clear();
        Field field = storage.getClass().getSuperclass().getDeclaredField("CAPACITY");
        int storageCapacity = field.getInt(field);
        try {
            for (int i = 0; i < storageCapacity; i++) {
                Resume r = new Resume();
                storage.save(r);
            }
        } catch (StorageException e) {
            fail("storage overflow");
        }
    }
}