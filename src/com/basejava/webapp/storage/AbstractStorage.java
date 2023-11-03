package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<T> implements Storage {

    private final static Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public void clear() {
        clearStorage();
    }

    public void update(Resume resume) {
        LOG.info("Update " + resume);
        T searchKey = getExistingSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
        System.out.println("Резюме " + resume.getUuid() + " обновлено");
    }

    public void save(Resume resume) {
        LOG.info("Save " + resume);
        T searchKey = getNotExistingSearchKey(resume.getUuid());
        doSave(resume, searchKey);
        System.out.println("Резюме " + resume.getFullName() + " добавлено в storage");
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        T searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public final void delete(String uuid) {
        LOG.info("Delete " + uuid);
        T searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
        System.out.println("Резюме " + uuid + " удалено");
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = getAll();
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    public int size() {
        return getStorageSize();
    }

    private T getExistingSearchKey(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private T getNotExistingSearchKey(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract boolean isExist(T searchKey);

    protected abstract T getSearchKey(String uuid);

    protected abstract void clearStorage();

    protected abstract void doUpdate(T searchKey, Resume resume);

    protected abstract Resume doGet(T searchKey) throws StorageException;

    protected abstract void doSave(Resume resume, T searchKey);

    protected abstract void doDelete(T searchKey);

    protected abstract int getStorageSize();

    protected abstract List<Resume> getAll();
}
