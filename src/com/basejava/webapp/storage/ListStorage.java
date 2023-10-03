package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {

    ArrayList<Resume> storage = new ArrayList<>();

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
    protected void updateResume(Object searchKey, Resume resume) {
        storage.set((int) searchKey, resume);
    }

    @Override
    protected void insertResume(Resume resume, Object searchKey) {
        storage.add(resume);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    protected void removeResume(Object searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected Resume[] getAllStorage() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int getStorageSize() {
        return storage.size();
    }

    @Override
    protected boolean isExist(String uuid) {
        return getSearchKey(uuid) != null;
    }
}