package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected final static int CAPACITY = 10_000;
    protected final Resume[] storage = new Resume[CAPACITY];
    protected int countResumes;

    public void clear() {
        clearStorage();
    }

    public void update(Resume resume) {
        Object searchKey = getExistingSearchKey(resume.getUuid());
        updateResume(searchKey, resume);
        System.out.println("Резюме " + resume.getUuid() + " обновлено");
    }

    public void save(Resume resume) {
        Object searchKey = getNotExistingSearchKey(resume.getUuid());
        insertResume(resume, searchKey);
        countResumes++;
        System.out.println("Резюме " + resume.getUuid() + " добавлено в storage");
    }

    public Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return getResume(searchKey);
    }

    public final void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        removeResume(searchKey);
        System.out.println("Резюме " + uuid + " удалено");
    }

    public Resume[] getAll() {
        return getAllStorage();
    }

    public int size() {
        return getStorageSize();
    }

    private Object getExistingSearchKey(String uuid) {
        if (!isExist(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        return getSearchKey(uuid);
    }

    private Object getNotExistingSearchKey(String uuid) {
        if (isExist(uuid)) {
            throw new ExistStorageException(uuid);
        }
        return getSearchKey(uuid);
    }

    protected abstract boolean isExist(String uuid);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void clearStorage();

    protected abstract void updateResume(Object searchKey, Resume resume);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void insertResume(Resume resume, Object searchKey);

    protected abstract void removeResume(Object searchKey);

    protected abstract Resume[] getAllStorage();

    protected abstract int getStorageSize();
}
