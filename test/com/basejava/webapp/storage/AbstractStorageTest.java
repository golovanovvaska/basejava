package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.ResumeTestData;
import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {

    protected final static File STORAGE_DIR = Config.getInstance().getStorageDir();

    private final Storage storage;

    private final static String UUID_1 = "uuid1";
    private final static String UUID_2 = "uuid2";
    private final static String UUID_3 = "uuid3";
    private final static String UUID_4 = "uuid4";
    private final static String NAME_1 = "name1";
    private final static String NAME_2 = "name2";
    private final static String NAME_3 = "name3";
    private final static String NAME_4 = "name4";
    private final static Resume RESUME_1 = ResumeTestData.createResume(UUID_1, NAME_1);
    private final static Resume RESUME_2 = ResumeTestData.createResume(UUID_2, NAME_2);
    private final static Resume RESUME_3 = ResumeTestData.createResume(UUID_3, NAME_3);
    private final static Resume RESUME_4 = ResumeTestData.createResume(UUID_4, NAME_4);

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        Resume[] resumes = {};
        assertArrayEquals(storage.getAllSorted().toArray(new Resume[0]), resumes);
    }

    @Test
    public void update() {
        Resume r = new Resume(UUID_1, NAME_1);
        storage.update(r);
        assertEquals(r, storage.get(UUID_1));
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    public void delete() {
        storage.delete(UUID_3);
        assertSize(2);
        NotExistStorageException exception = assertThrows(NotExistStorageException.class, () -> storage.get(UUID_3));
        assertEquals("Резюме uuid3 нет в storage", exception.getMessage());
    }

    @Test
    public void getAll() {
        Resume[] resumes = {RESUME_1, RESUME_2, RESUME_3};
        List<Resume> expected = Arrays.asList(resumes);
        expected.sort((o1, o2) -> {
            if (o1.getFullName().equals(o2.getFullName())) {
                return o1.getUuid().compareTo(o2.getUuid());
            }
            return o1.getFullName().compareTo(o2.getFullName());

        });
        assertEquals(expected, storage.getAllSorted());
    }

    @Test
    public void size() {
        storage.clear();
        assertSize(0);
        setUp();
        assertSize(3);
    }

    @Test()
    public void storageOverflow() {
        storage.clear();
        int storageCapacity = AbstractArrayStorage.CAPACITY;
        try {
            for (int i = 0; i < storageCapacity; i++) {
                Resume r = new Resume("fullName");
                storage.save(r);
            }
        } catch (StorageException e) {
            fail("storage overflow");
        }
        assertThrows(StorageException.class, () -> {
            Resume r = new Resume("fullName");
            storage.save(r);
        });
    }

    @Test
    public void getNotExist() {
        NotExistStorageException exception = assertThrows(NotExistStorageException.class, () -> storage.get(UUID_4));
        assertEquals("Резюме uuid4 нет в storage", exception.getMessage());
    }

    @Test
    public void deleteNotExist() {
        NotExistStorageException exception = assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_3);
            storage.get(UUID_3);
        });
        assertEquals("Резюме uuid3 нет в storage", exception.getMessage());
    }

    @Test
    public void updateNotExist() {
        NotExistStorageException exception = assertThrows(NotExistStorageException.class, () -> storage.update(RESUME_4));
        assertEquals("Резюме uuid4 нет в storage", exception.getMessage());
    }

    @Test
    public void saveExist() {
        ExistStorageException exception = assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
        assertEquals("Резюме uuid1 уже есть в storage", exception.getMessage());
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}