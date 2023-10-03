package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void clear() {
        clearStorage();
    }

    public void update(Resume resume) {
        Object searchKey = getExistingSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
        System.out.println("Резюме " + resume.getUuid() + " обновлено");
    }

    public void save(Resume resume) {
        Object searchKey = getNotExistingSearchKey(resume.getUuid());
        doSave(resume, searchKey);
        System.out.println("Резюме " + resume.getUuid() + " добавлено в storage");
    }

    public Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public final void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
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

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doSave(Resume resume, Object searchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract Resume[] getAllStorage();

    protected abstract int getStorageSize();
}
