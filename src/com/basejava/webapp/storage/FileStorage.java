package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.strategies.Strategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private final File directory;
    private final Strategy strategy;

    protected FileStorage(File directory, Strategy strategy) {
        this.strategy = strategy;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }


    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void clearStorage() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException(directory.getAbsolutePath() + "is empty", "");
        }
        for (File file : files) {
            doDelete(file);
        }
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            strategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return strategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
            doUpdate(file, resume);
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete())
            throw new StorageException("Deletion Error", file.getName());
    }

    @Override
    protected int getStorageSize() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException(directory.getAbsolutePath() + "is empty", "");
        }
        return files.length;
    }

    @Override
    protected List<Resume> getAll() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException(directory.getAbsolutePath() + "is empty", "");
        }
        List<Resume> resumes = new ArrayList<>();
        for (File file : files) {
            resumes.add(doGet(file));
        }
        return resumes;
    }
}
