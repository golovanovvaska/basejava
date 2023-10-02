package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {

    ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected int getIndex(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected void clearStorage() {
        storage.clear();
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    protected void removeResume(int index) {
        storage.remove(index);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
