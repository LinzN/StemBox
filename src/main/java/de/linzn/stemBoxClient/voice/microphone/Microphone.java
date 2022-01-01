package de.linzn.stemBoxClient.voice.microphone;


import de.linzn.stemBoxClient.StemBoxApp;

import javax.sound.sampled.*;
import javax.sound.sampled.Mixer.Info;
import java.util.Date;

public class Microphone {


    private final int deviceIndex;
    private final AudioFormat audioFormat;
    private TargetDataLine line;

    public Microphone(AudioFormat audioFormat, int deviceIndex) {
        this.audioFormat = audioFormat;
        this.deviceIndex = deviceIndex;
    }

    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    public void open() {
        try {
            StemBoxApp.LOGGER.CONFIG("Open Microphone");
            Info info = AudioSystem.getMixerInfo()[deviceIndex];
            line = AudioSystem.getTargetDataLine(audioFormat, info);
            //line.addLineListener(listener);
            line.open(audioFormat);

        } catch (LineUnavailableException ex) {
            StemBoxApp.LOGGER.ERROR(ex);
        }
    }


    public AudioInputStream start() {
        StemBoxApp.LOGGER.CONFIG("Start Microphone");
        AudioInputStream audioInputStream = new AudioInputStream(line);
        line.start();
        //return new MicrophoneInputStream(audioInputStream);
        return audioInputStream;
    }


    public void stop() {
        line.stop();
        StemBoxApp.LOGGER.INFO("Stop recording..." + new Date());
    }

    public void close() {
        line.stop();
        line.close();
        StemBoxApp.LOGGER.INFO("Data line closed");
    }
}