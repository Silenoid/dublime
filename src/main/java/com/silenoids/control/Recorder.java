package com.silenoids.control;

import com.silenoids.utils.FileUtils;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Recorder implements Runnable {
    private AudioInputStream audioInputStream;
    private AudioFormat format;
    public Thread thread;
    private double duration;

    public void startWithAlias(String dirPath, String fileName) {
        File aliasAudioFile = FileUtils.loadDirFile(dirPath, fileName);

        try {
            AudioFileFormat aliasFileFormat = AudioSystem.getAudioFileFormat(aliasAudioFile);

            if(aliasFileFormat.getFormat().getSampleRate() == 22050) {
                format = new AudioFormat(
                        22000,
                        16,
                        1,
                        true,
                        true
                );
            } else {
                format = aliasFileFormat.getFormat();
            }

            long frameLength = aliasFileFormat.getFrameLength();
            // TODO: let the duration depend on this

            thread = new Thread(this);
            thread.setName("Capture Microphone");
            thread.start();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        thread = null;
    }

    @Override
    public void run() {
        duration = 0;

        try (final ByteArrayOutputStream out = new ByteArrayOutputStream(); final TargetDataLine line = getTargetDataLineForRecord()) {
            int frameSizeInBytes = format.getFrameSize();
            int bufferLengthInFrames = line.getBufferSize() / 8;
            final int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
            buildByteOutputStream(out, line, frameSizeInBytes, bufferLengthInBytes);
            this.audioInputStream = new AudioInputStream(line);
            setAudioInputStream(convertToAudioIStream(out, frameSizeInBytes));
            audioInputStream.reset();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void buildByteOutputStream(final ByteArrayOutputStream out, final TargetDataLine line, int frameSizeInBytes, final int bufferLengthInBytes) {
        final byte[] data = new byte[bufferLengthInBytes];
        int numBytesRead;

        line.start();
        while (thread != null) {
            if ((numBytesRead = line.read(data, 0, bufferLengthInBytes)) == -1) {
                break;
            }
            out.write(data, 0, numBytesRead);
        }
    }

    private void setAudioInputStream(AudioInputStream aStream) {
        this.audioInputStream = aStream;
    }

    public AudioInputStream convertToAudioIStream(final ByteArrayOutputStream out, int frameSizeInBytes) {
        byte[] audioBytes = out.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
        AudioInputStream audioStream = new AudioInputStream(bais, format, audioBytes.length / frameSizeInBytes);
        long milliseconds = (long) ((audioInputStream.getFrameLength() * 1000) / format.getFrameRate());
        duration = milliseconds / 1000.0;
        System.out.println("Recorded duration in seconds:" + duration);
        return audioStream;
    }

    public TargetDataLine getTargetDataLineForRecord() {
        TargetDataLine line;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) {
            return null;
        }
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format, line.getBufferSize());
        } catch (final Exception ex) {
            return null;
        }
        return line;
    }

    public AudioInputStream getAudioInputStream() {
        return audioInputStream;
    }

    public AudioFormat getFormat() {
        return format;
    }

    public void setFormat(AudioFormat format) {
        this.format = format;
    }

    public Thread getThread() {
        return thread;
    }

    public double getDuration() {
        return duration;
    }

}
