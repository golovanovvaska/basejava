package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;
import java.util.Arrays;
/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final static int CAPACITY = 10_000;
    private Resume[] storage = new Resume[CAPACITY];
    private int countResumes;

    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    public void save(Resume resume) {
        if (countResumes < CAPACITY) {
            int index = getIndex(resume.getUuid());
            if (index == -1) {
                storage[countResumes] = resume;
                countResumes++;
                System.out.println("Резюме " + resume.getUuid() + " добавлено в storage");
            } else {
                System.out.println("Резюме " + resume.getUuid() + " уже есть в storage");
            }
        } else {
            System.out.println("storage полон");
        }
    }

    public Resume get(String uuid) {
        if (getIndex(uuid) != -1) {
            return storage[getIndex(uuid)];
        }
        System.out.println("Резюме " + uuid + " нет в storage");
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            System.arraycopy(storage, index + 1, storage, index, countResumes - index - 1);
            storage[countResumes - 1] = null;
            countResumes--;
            System.out.println("Резюме " + uuid + " удалено");
        } else {
            System.out.println("Резюме " + uuid + " нет в storage");
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
            System.out.println("Резюме " + resume.getUuid() + " обновлено");
        } else {
            System.out.println("Резюме " + resume.getUuid() + " нет в storage");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    public int size() {
        return countResumes;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
