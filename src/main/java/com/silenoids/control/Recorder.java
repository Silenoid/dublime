package com.silenoids.control;

import com.silenoids.utils.FileUtils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Recorder {

    private final AudioFormat outputLineFormat;
    private TargetDataLine micLine;

    private CyclicBarrier synchronizedGate;
    private Thread stopperThread;
    private Thread writerThread;

    public Recorder() {
        outputLineFormat = new AudioFormat(
                22050,
                16,
                1,
                true,
                true
        );

        try {
            setUp();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        synchronizedGate = new CyclicBarrier(2);
    }

    public void recordAudio(String dirPath, String fileName, long millisToRecord) {
        File targetOutputFile = FileUtils.loadDirFile(dirPath, fileName);
        AudioInputStream audioInputStream = new AudioInputStream(micLine);

        planToStopRecording(millisToRecord);
        recordAudio(audioInputStream, targetOutputFile);

        System.out.println("---Running thread list:");
        Thread.getAllStackTraces().keySet().stream().map(Thread::getName).filter(s -> s.startsWith(" ")).sorted().forEach(System.out::println);

        Sandglass.getInstance().startSandglass(millisToRecord);
    }

    private void setUp() throws LineUnavailableException {
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, outputLineFormat);
        if (!AudioSystem.isLineSupported(info)) {
            throw new LineUnavailableException("No line supported for the DataLine Info");
        }
        micLine = (TargetDataLine) AudioSystem.getLine(info);
        micLine.open(outputLineFormat, micLine.getBufferSize());
    }

    private void planToStopRecording(long millis) {
        if (stopperThread == null) {
            stopperThread = new Thread(() -> {
                System.out.println("started stopper per millis: " + millis);
                try {
                    synchronizedGate.await();
                    micLine.start();
                    Thread.sleep(millis);
                } catch (InterruptedException | BrokenBarrierException ex) {
                    ex.printStackTrace();
                } finally {
                    micLine.stop();
                }
            }, " Recording stopper");
        }

        stopperThread.start();
    }

    private void recordAudio(AudioInputStream audioInputStream, File targetOutputFile) {
        if (writerThread == null) {
            writerThread = new Thread(() -> {
                System.out.println("started writer su output " + targetOutputFile);

                try {
                    synchronizedGate.await();
                    AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, targetOutputFile);
                } catch (InterruptedException | BrokenBarrierException | IOException ex) {
                    ex.printStackTrace();
                }
            }, " Recording writing");
        }

        writerThread.start();
    }

}
