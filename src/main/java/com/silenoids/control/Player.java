package com.silenoids.control;

import com.silenoids.utils.FileUtils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Player {

    private AudioInputStream stream;
    private final AtomicReference<Clip> audioClip = new AtomicReference<>();
    private final AtomicBoolean isPlaying = new AtomicBoolean(false);

    private Runnable playingRunnable;
    private Thread playingThread;

    public Player() {
        isPlaying.set(false);

        playingRunnable = () -> {
            try {
                isPlaying.set(true);
                audioClip.get().start();
                while (isPlaying.get() && !audioClip.get().isRunning()) Thread.sleep(10);
                while (isPlaying.get() && audioClip.get().isRunning()) Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Listening thread has been interrupted");
            } finally {
                audioClip.get().close();
                isPlaying.set(false);
            }
        };
    }

    public void loadAudioFile(String dirPath, String fileName) {
        File audioFile = FileUtils.loadDirFile(dirPath, fileName);

        if (audioClip.get() != null && audioClip.get().isRunning()) {
            audioClip.get().stop();
            audioClip.get().drain();
        }

        try {
            stream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat audioFormat = stream.getFormat();
            DataLine.Info inputClipInfo = new DataLine.Info(Clip.class, audioFormat);
            audioClip.set((Clip) AudioSystem.getLine(inputClipInfo));
            audioClip.get().open(stream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    public void play() {
        if (isPlaying.get()) {
            stop();
        }
        playingThread = new Thread(playingRunnable,  " Playing audio");
        playingThread.start();
        Sandglass.getInstance().startSandglass(getDurationInMillis());
    }

    public void stop() {
        if(playingThread != null) {
            isPlaying.set(false);
            try {
                playingThread.join();
                if (stream != null) {
                    stream.close();
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getDurationText() {
        String durationText = "-.--";
        if (audioClip.get() != null) {
            durationText = String.valueOf(audioClip.get().getMicrosecondLength() / 1000000.0).substring(0, 4) + " seconds";
        }
        return durationText;
    }

    public long getDurationInMillis() {
        return audioClip.get() != null ? (long) (audioClip.get().getMicrosecondLength() / 1000.0) : 0;
    }

}
