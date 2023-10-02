package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractStorage implements Storage {

    protected final static int CAPACITY = 10_000;
    protected final Resume[] storage = new Resume[CAPACITY];
    protected int countResumes;

    public void clear() {
        clearStorage();
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            updateResume(index, resume);
            System.out.println("Резюме " + resume.getUuid() + " обновлено");
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (countResumes >= CAPACITY) {
            throw new StorageException("storage полон", resume.getUuid());
        } else if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            insertResume(resume, index);
            countResumes++;
            System.out.println("Резюме " + resume.getUuid() + " добавлено в storage");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return getResume(index);
        }
        throw new NotExistStorageException(uuid);
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            removeResume(index);
            System.out.println("Резюме " + uuid + " удалено");
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    public int size() {
        return countResumes;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void clearStorage();

    protected abstract void updateResume(int index, Resume resume);

    protected abstract Resume getResume(int index);

    protected abstract void insertResume(Resume resume, int index);

    protected abstract void removeResume(int index);
}
