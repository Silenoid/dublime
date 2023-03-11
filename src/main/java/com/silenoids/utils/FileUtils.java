package com.silenoids.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    public static File loadDirFile(String dirPath, String fileName) {
        return new File(dirPath + File.separator + fileName);
    }

    public static boolean fileExists(String dirPath, String fileName) {
        return Files.exists(Paths.get(dirPath + File.separator + fileName));
    }

}
