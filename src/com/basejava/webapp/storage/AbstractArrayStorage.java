package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

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
        insertResume(resume, searchKey);
        countResumes++;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected void doDelete(Object searchKey) {
        copyResume((int) searchKey);
        storage[countResumes - 1] = null;
        countResumes--;
    }

    @Override
    protected Resume[] getAllStorage() {
        return Arrays.copyOf(storage, countResumes);
    }

    @Override
    protected boolean isExist(String uuid) {
        return (int) getSearchKey(uuid) >= 0;
    }

    @Override
    protected int getStorageSize() {
        return countResumes;
    }

    protected abstract void copyResume(int index);

    protected abstract void insertResume(Resume resume, Object searchKey);
}
