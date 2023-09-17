package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(Resume resume, int index) {
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, countResumes - index);
        storage[index] = resume;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume resume = new Resume();
        resume.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, countResumes, resume);
    }
}
