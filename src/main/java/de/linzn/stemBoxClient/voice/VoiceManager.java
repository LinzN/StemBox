package de.linzn.stemBoxClient.voice;

import de.linzn.stemBoxClient.StemBoxApp;
import de.linzn.stemBoxClient.voice.microphone.Microphone;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.util.concurrent.Executors;

public class VoiceManager {

    private final Microphone microphone;
    private final MicrophoneStreamListener microphoneStreamListener;
    private final StemBoxApp stemBoxApp;
    private AudioInputStream microphoneInputStream;

    public VoiceManager(StemBoxApp stemBoxApp) {
        this.stemBoxApp = stemBoxApp;
        this.microphone = new Microphone(getAudioFormat(), 1);
        this.microphoneStreamListener = new MicrophoneStreamListener(this);
    }

    public static AudioFormat getAudioFormat() {
        return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 60000, 16, 2, 4, 44100, false);
    }

    public void enable() {
        this.microphone.open();
        this.microphoneInputStream = this.microphone.start();
        Executors.newSingleThreadExecutor().submit(microphoneStreamListener);
    }

    public void disable() {
        this.microphoneStreamListener.stop();
        this.microphone.stop();
        this.microphone.close();
    }

    public AudioInputStream getMicrophoneInputStream() {
        return microphoneInputStream;
    }

    public StemBoxApp getStemBoxApp() {
        return stemBoxApp;
    }
}
