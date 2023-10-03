package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(Resume resume, Object searchKey) {
        int index = -(int) searchKey - 1;
        System.arraycopy(storage, index, storage, index + 1, countResumes - index);
        storage[index] = resume;
    }

    @Override
    protected void copyResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, countResumes - index - 1);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume resume = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, countResumes, resume);
    }
}
