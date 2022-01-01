package de.linzn.stemBoxClient.stemLinkVoice;

import de.linzn.stemBoxClient.StemBoxApp;

public class StemLinkVoiceManager {
    public StemVoiceSocket stemVoiceSocket;

    private StemBoxApp stemBoxApp;

    public StemLinkVoiceManager(StemBoxApp stemBoxApp) {
        this.stemBoxApp = stemBoxApp;
        this.stemVoiceSocket = new StemVoiceSocket();
    }

    public void enable() {
        this.stemVoiceSocket.enable();
    }

    public void disable() {
        this.stemVoiceSocket.disable();
    }

    public StemBoxApp getStemBoxApp() {
        return stemBoxApp;
    }
}
