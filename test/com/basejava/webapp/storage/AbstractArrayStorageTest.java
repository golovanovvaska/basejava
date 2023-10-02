package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest {

    private final Storage storage;
    private final static String UUID_1 = "uuid1";
    private final static String UUID_2 = "uuid2";
    private final static String UUID_3 = "uuid3";
    private final static String UUID_4 = "uuid4";
    private final static Resume RESUME_1 = new Resume(UUID_1);
    private final static Resume RESUME_2 = new Resume(UUID_2);
    private final static Resume RESUME_3 = new Resume(UUID_3);
    private final static Resume RESUME_4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        Resume[] resumes = {};
        assertArrayEquals(storage.getAll(), resumes);
    }

    @Test
    void update() {
        Resume r = new Resume(UUID_1);
        storage.update(r);
        assertSame(r, storage.get(UUID_1));
    }

    @Test
    void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test
    void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    void delete() {
        storage.delete(UUID_3);
        assertSize(2);
        NotExistStorageException exception = assertThrows(NotExistStorageException.class, () -> {
            storage.get(UUID_3);
        });
        assertEquals("Резюме uuid3 нет в storage", exception.getMessage());
    }

    @Test
    void getAll() {
        Resume[] expected = {RESUME_1, RESUME_2, RESUME_3};
        assertArrayEquals(expected, storage.getAll());
    }

    @Test
    void size() {
        storage.clear();
        assertSize(0);
        setUp();
        assertSize(3);
    }

    @Test()
    void storageOverflow() {
        storage.clear();
        int storageCapacity = AbstractArrayStorage.CAPACITY;
        try {
            for (int i = 0; i < storageCapacity; i++) {
                Resume r = new Resume();
                storage.save(r);
            }
        } catch (StorageException e) {
            fail("storage overflow");
        }
        assertThrows(StorageException.class, () -> {
            Resume r = new Resume();
            storage.save(r);
        });
    }

    @Test
    void getNotExist() {
        NotExistStorageException exception = assertThrows(NotExistStorageException.class, () -> {
            storage.get(UUID_4);
        });
        assertEquals("Резюме uuid4 нет в storage", exception.getMessage());
    }

    @Test
    void deleteNotExist() {
        NotExistStorageException exception = assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_3);
            storage.get(UUID_3);
        });
        assertEquals("Резюме uuid3 нет в storage", exception.getMessage());
    }

    @Test
    void updateNotExist() {
        NotExistStorageException exception = assertThrows(NotExistStorageException.class, () -> {
            storage.update(RESUME_4);
        });
        assertEquals("Резюме uuid4 нет в storage", exception.getMessage());
    }

    @Test
    void saveExist() {
        ExistStorageException exception = assertThrows(ExistStorageException.class, () -> {
            storage.save(RESUME_1);
        });
        assertEquals("Резюме uuid1 уже есть в storage", exception.getMessage());
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}