package com.basejava.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = "./.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("--------------------------------------------------------------------");
        printFileList(dir);
    }

    private static void printFileList(File dir) {
        String[] dirList = dir.list();
        if (dirList != null) {
            for (String s : dirList) {
                File f = new File(dir + "/" + s);
                if (f.isDirectory()) {
                    printFileList(f);
                } else {
                    System.out.println(f.getName());
                }
            }
        }
    }
}
