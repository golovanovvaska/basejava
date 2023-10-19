package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(Resume resume, Integer index) {
        storage[countResumes] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        storage[index] = storage[countResumes - 1];
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
