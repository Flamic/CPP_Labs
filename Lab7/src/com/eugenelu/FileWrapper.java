package com.eugenelu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@AllArgsConstructor
@Getter
@Setter
public class FileWrapper {
    private File file;

    public boolean exists() {
        return file != null;
    }

    public String getAbsolutePath() {
        return file == null ? "" : file.getAbsolutePath();
    }

    public FileWrapper copy() {
        return new FileWrapper(new File(file.getAbsolutePath()));
    }
}
