package com.silenoids.control;

import com.silenoids.utils.FileUtils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class RecorderOld {

    public void recordAudio(String dirPath, String fileName, long millisToRecord) {
        File targetOutputFile = FileUtils.loadDirFile(dirPath, fileName);

        writeAudioToFileBlocking(targetOutputFile, millisToRecord);

        printThread();

        Sandglass.getInstance().startSandglass(millisToRecord);
    }

    private void writeAudioToFile(File targetOutputFile, long millisToRecord) {
        new Thread(() -> {
            try {
                AudioFormat format = new AudioFormat(
                        22050,
                        16,
                        1,
                        true,
                        true
                );
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                if (!AudioSystem.isLineSupported(info)) {
                    throw new LineUnavailableException("No line supported for the DataLine Info");
                }
                TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
                line.open(format);

                while(!line.isOpen()) Thread.sleep(10);

                AudioInputStream iStream = new AudioInputStream(line);

                Timer timer = new Timer("Record stopper timer");
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        line.stop();
                        line.close();
                    }
                }, millisToRecord);

                line.start();
                AudioSystem.write(iStream, AudioFileFormat.Type.WAVE, targetOutputFile);

            } catch (IOException | LineUnavailableException | InterruptedException ex) {
                ex.printStackTrace();
            }
        }, " Recording writing").start();
    }

    private void writeAudioToFileBlocking(File targetOutputFile, long millisToRecord) {
        try {
            AudioFormat format = new AudioFormat(
                    22050,
                    16,
                    1,
                    true,
                    true
            );
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                throw new LineUnavailableException("No line supported for the DataLine Info");
            }
            TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);

            while(!line.isOpen()) Thread.sleep(10);

            System.out.println("millis: " + millisToRecord);
            System.out.println("line is open");

            line.start();

            AudioInputStream iStream = new AudioInputStream(line);

            Thread stopperThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("started waiting to stop");
                    try {
                        Thread.sleep(millisToRecord);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("stopping");

                    line.stop();
                    line.close();

                    System.out.println("stopped");


                }
            });
            stopperThread.start();

            System.out.println("gonna line start");

            AudioSystem.write(iStream, AudioFileFormat.Type.WAVE, targetOutputFile);

        } catch (IOException | LineUnavailableException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void printThread() {
        System.out.println("---Running thread list:");
        Thread.getAllStackTraces().keySet().stream().map(Thread::getName).filter(s -> s.startsWith(" ")).sorted().forEach(System.out::println);
    }
}
