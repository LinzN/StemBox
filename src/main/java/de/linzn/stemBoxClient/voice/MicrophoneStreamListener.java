package de.linzn.stemBoxClient.voice;

import de.linzn.stemBoxClient.StemBoxApp;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;

public class MicrophoneStreamListener implements Runnable {

    private final VoiceManager voiceManager;
    private boolean running = false;

    public MicrophoneStreamListener(VoiceManager voiceManager) {
        this.voiceManager = voiceManager;
    }

    @Override
    public void run() {
        this.running = true;
        AudioInputStream microphoneInputStream = voiceManager.getMicrophoneInputStream();
        byte[] buffer = new byte[2048];

        StemBoxApp.LOGGER.CONFIG("Start MicrophoneStreamListener...");
        while (running) {
            try {
                int dataRead = microphoneInputStream.read(buffer);
                if (this.voiceManager.getStemBoxApp().stemLinkVoiceManager.stemVoiceSocket.isValidConnection()) {
                    this.voiceManager.getStemBoxApp().stemLinkVoiceManager.stemVoiceSocket.writeData(buffer, 0, dataRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        this.running = false;
    }
}
