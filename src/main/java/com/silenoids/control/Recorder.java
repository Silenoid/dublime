package com.silenoids.control;

import com.silenoids.utils.FileUtils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Recorder {

    private AudioFormat outputLineFormat;
    private TargetDataLine micLine;

    private CyclicBarrier synchronizedGate;

    public Recorder() {
        outputLineFormat = new AudioFormat(
                22050,
                16,
                1,
                true,
                true
        );

        synchronizedGate = new CyclicBarrier(2);
    }

    public void recordAudio(String dirPath, String fileName, long millisToRecord) {
        try {
            File targetOutputFile = FileUtils.loadDirFile(dirPath, fileName);
            DataLine.Info outputLineInfo = new DataLine.Info(TargetDataLine.class, outputLineFormat);
            micLine = (TargetDataLine) AudioSystem.getLine(outputLineInfo);
            if (!AudioSystem.isLineSupported(outputLineInfo)) {
                System.out.println("Line not supported");
                return;
            }

            micLine.open(outputLineFormat, micLine.getBufferSize());

            AudioInputStream audioInputStream = new AudioInputStream(micLine);

            planToStopRecording(millisToRecord);
            recordAudio(audioInputStream, targetOutputFile);

            Thread.getAllStackTraces().keySet().stream().map(Thread::getName).sorted().forEach(System.out::println);

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }

        Sandglass.getInstance().startSandglass(millisToRecord);
    }

    private void planToStopRecording(long millis) {
        new Thread(() -> {
            try {
                synchronizedGate.await();
                micLine.start();
                Thread.sleep(millis);
            } catch (InterruptedException | BrokenBarrierException ex) {
                ex.printStackTrace();
            }
            micLine.stop();
            micLine.close();
        }, " Recording stopper").start();
    }

    private void recordAudio(AudioInputStream audioInputStream, File targetOutputFile) {
        new Thread(() -> {
            try {
                synchronizedGate.await();
                AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, targetOutputFile);
            } catch (InterruptedException | BrokenBarrierException | IOException ex) {
                ex.printStackTrace();
            }
        }, " Recording writing").start();
    }

}
