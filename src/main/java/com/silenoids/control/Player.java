package com.silenoids.control;

import com.goxr3plus.streamplayer.stream.StreamPlayer;
import com.goxr3plus.streamplayer.stream.StreamPlayerEvent;
import com.goxr3plus.streamplayer.stream.StreamPlayerException;
import com.goxr3plus.streamplayer.stream.StreamPlayerListener;
import com.silenoids.utils.FileUtils;

import java.io.File;
import java.util.Map;

public class Player extends StreamPlayer implements StreamPlayerListener {

    private boolean canActuallyPlay = false;

    public Player() {
        addStreamPlayerListener(this);
    }

    public void loadAudioFile(String dirPath, String fileName) {
        super.stop();

        try {
            File audioFile = FileUtils.loadDirFile(dirPath, fileName);
            super.open(audioFile);
        } catch (StreamPlayerException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (isPlaying()) {
            stop();
        }
        try {
            super.play();
        } catch (StreamPlayerException e) {
            e.printStackTrace();
        }
        Sandglass.getInstance().startSandglass(getDurationInMillis());
    }

    public void stop() {
        super.stop();
        while (!canActuallyPlay) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Sandglass.getInstance().stopSandglass();
    }

    public String getDurationText() {
        return (getDurationInMillis() / 1000.0) + " seconds";
    }

    public long getDurationInMillis() {
        return super.getDurationInMilliseconds();
    }

    @Override
    public void opened(Object o, Map<String, Object> map) {

    }

    @Override
    public void progress(int i, long l, byte[] bytes, Map<String, Object> map) {

    }

    @Override
    public void statusUpdated(StreamPlayerEvent streamPlayerEvent) {
        switch (streamPlayerEvent.getPlayerStatus()) {
            case PLAYING:
                canActuallyPlay = false;
                break;
            case STOPPED:
                canActuallyPlay = true;
                break;
        }
    }
}
