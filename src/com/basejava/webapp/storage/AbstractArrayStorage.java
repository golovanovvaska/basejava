package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected final static int CAPACITY = 10_000;
    protected final Resume[] storage = new Resume[CAPACITY];
    protected int countResumes;

    @Override
    protected void clearStorage() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
    }

    @Override
    protected void doSave(Resume resume, Object searchKey){
        try {
            insertResume(resume, searchKey);
            countResumes++;
        } catch (RuntimeException e) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected void doDelete(Object searchKey) {
        deleteResume((int) searchKey);
        storage[countResumes - 1] = null;
        countResumes--;
    }

    @Override
    protected List<Resume> getAllStorage() {
        return Arrays.stream(Arrays.copyOf(storage, countResumes)).toList();
    }

    @Override
    protected boolean isExist(Object uuid) {
        return (int) uuid >= 0;
    }

    @Override
    protected int getStorageSize() {
        return countResumes;
    }

    protected abstract void deleteResume(int index);

    protected abstract void insertResume(Resume resume, Object searchKey);
}
