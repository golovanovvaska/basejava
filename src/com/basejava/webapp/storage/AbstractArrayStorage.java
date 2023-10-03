package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    @Override
    protected void clearStorage() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    @Override
    protected void updateResume(Object searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected void removeResume(Object searchKey) {
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

    protected int getStorageSize() {
        return countResumes;
    }

    protected abstract void copyResume(int index);
}
