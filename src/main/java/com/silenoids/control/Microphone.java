package com.silenoids.control;

import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;

public class Microphone {

    private final Mixer mixer;
    private final Mixer.Info mixerInfo;
    private final Line line;
    private final Line.Info lineInfo;

    public Microphone(Mixer mixer, Mixer.Info mixerInfo, Line line, Line.Info lineInfo) {
        this.mixer = mixer;
        this.mixerInfo = mixerInfo;
        this.line = line;
        this.lineInfo = lineInfo;
    }

    public Mixer getMixer() {
        return mixer;
    }

    public Mixer.Info getMixerInfo() {
        return mixerInfo;
    }

    public Line getLine() {
        return line;
    }

    public Line.Info getLineInfo() {
        return lineInfo;
    }

    @Override
    public String toString() {
        return mixerInfo.getName();
    }
}
