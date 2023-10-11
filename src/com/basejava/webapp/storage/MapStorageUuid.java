package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class MapStorageUuid extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void clearStorage() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected int getStorageSize() {
        return storage.size();
    }

    @Override
    protected List<Resume> getSorted() {
        List<Resume> list = new ArrayList<>(storage.values());
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    @Override
    protected boolean isExist(Object uuid) {
        return storage.containsKey((String) uuid);
    }
}
