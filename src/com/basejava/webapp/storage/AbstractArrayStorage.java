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
    protected void updateResume(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected Resume getResume(int index) {
        return storage[index];
    }

    @Override
    protected void removeResume(int index) {
        copyResume(index);
        storage[countResumes - 1] = null;
        countResumes--;
    }

    protected abstract void copyResume(int index);
}
