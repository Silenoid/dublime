package com.silenoids.utils;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    public static File loadDirFile(String dirPath, String fileName) {
        return new File(dirPath + File.separator + fileName);
    }

    public static boolean fileExists(String dirPath, String fileName) {
        return Files.exists(Paths.get(dirPath + File.separator + fileName));
    }

    public static boolean saveAudioStreamToFile(String dirPath, String fileName, AudioInputStream audioInputStream) {
        AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
        File targetOutputFile = FileUtils.loadDirFile(dirPath, fileName);

        try {
            audioInputStream.reset();
            AudioSystem.write(audioInputStream, fileType, targetOutputFile);
        } catch (IOException e) {
            return false;
        }
        return true;
    }


}
