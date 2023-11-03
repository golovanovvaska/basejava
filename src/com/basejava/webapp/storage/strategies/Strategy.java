package com.basejava.webapp.storage.strategies;

import com.basejava.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Strategy {
    public abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    public abstract Resume doRead(InputStream is) throws IOException;
}
