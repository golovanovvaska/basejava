package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(Resume resume, Integer searchKey) {
        int index = -(int) searchKey - 1;
        System.arraycopy(storage, index, storage, index + 1, countResumes - index);
        storage[index] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, countResumes - index - 1);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume resume = new Resume(uuid, "qwerty");
        return Arrays.binarySearch(storage, 0, countResumes, resume);
    }
}
