package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected Object getSearchKey(String uuid) {
        for (Resume r : storage) {
            if (r.getUuid().equals(uuid)) {
                return storage.indexOf(r);
            }
        }
        return null;
    }

    @Override
    protected void clearStorage() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        storage.set((int) searchKey, resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.add(resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    public int getStorageSize() {
        return storage.size();
    }

    @Override
    protected boolean isExist(Object uuid) {
        return uuid != null;
    }

    @Override
    protected List<Resume> getSorted() {
        storage.sort(RESUME_COMPARATOR);
        return storage;
    }
}
