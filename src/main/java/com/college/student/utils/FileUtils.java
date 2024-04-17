package com.college.student.utils;

import java.io.*;
import java.util.List;

public class FileUtils<T> {
    private final File file;

    public FileUtils(File file) {
        this.file = file;
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeObject(List<T> objectList) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(this.file));
            objectOutputStream.writeObject(objectList);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public List<T> readObject() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(this.file));
            return (List<T>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
        return null;
    }
}
