package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

    protected final static int CAPACITY = 10_000;
    protected final Resume[] storage = new Resume[CAPACITY];
    protected int countResumes;

    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            System.out.println("Резюме " + resume.getUuid() + " обновлено");
        } else {
            System.out.println("Резюме " + resume.getUuid() + " нет в storage");
        }
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (countResumes >= CAPACITY) {
            System.out.println("storage полон");
        } else if (index >= 0) {
            System.out.println("Резюме " + resume.getUuid() + " уже есть в storage");
        } else {
            insertResume(resume, index);
            countResumes++;
            System.out.println("Резюме " + resume.getUuid() + " добавлено в storage");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("Резюме " + uuid + " нет в storage");
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            copyResume(index);
            storage[countResumes - 1] = null;
            countResumes--;
            System.out.println("Резюме " + uuid + " удалено");
        } else {
            System.out.println("Резюме " + uuid + " нет в storage");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    public int size() {
        return countResumes;
    }

    protected abstract void insertResume(Resume resume, int index);

    protected abstract void copyResume(int index);

    protected abstract int getIndex(String uuid);


}
