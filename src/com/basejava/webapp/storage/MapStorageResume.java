package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class MapStorageResume extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void clearStorage() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Object searchResume, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(Object resume) {
        String key = null;
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (entry.getValue().equals(resume)) {
                key = entry.getKey();
            }
        }
        storage.remove(key);
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
}
