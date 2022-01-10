package ru.job4j.concurrent.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class SaveContent implements Save {

    private final File file;

    public SaveContent(File file) {
        this.file =  file;
    }

    @Override
    public synchronized void save(String content)  {
        try (BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
