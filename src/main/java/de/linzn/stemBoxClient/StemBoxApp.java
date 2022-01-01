/*
 * Copyright (C) 2019. Niklas Linz - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.stemBoxClient;

import de.linzn.simplyLogger.LogSystem;
import de.linzn.simplyLogger.Logger;
import de.linzn.stemBoxClient.led.LEDControl;
import de.linzn.stemBoxClient.stemLink.StemLinkManager;
import de.linzn.stemBoxClient.stemLinkVoice.StemLinkVoiceManager;
import de.linzn.stemBoxClient.voice.VoiceManager;

import java.io.File;
import java.util.logging.Level;

public class StemBoxApp {
    public static StemBoxApp stemBoxApp;
    public static Logger LOGGER;
    public static LogSystem logSystem;
    public AppConfiguration appConfiguration;
    public StemLinkManager stemLinkManager;
    public VoiceManager voiceManager;
    public StemLinkVoiceManager stemLinkVoiceManager;
    public LEDControl ledControl;

    public StemBoxApp() {
        stemBoxApp = this;
        this.appConfiguration = new AppConfiguration(this);
        this.ledControl = new LEDControl(this);
        this.stemLinkManager = new StemLinkManager(this);
        this.voiceManager = new VoiceManager(this);
        this.stemLinkVoiceManager = new StemLinkVoiceManager(this);

        this.ledControl.enable();
        this.stemLinkManager.enable();
        this.stemLinkVoiceManager.enable();
        this.voiceManager.enable();
        registerShutdown();
    }

    public static void main(String[] args) {
        logSystem = new LogSystem("StemBox");
        logSystem.setFileLogger(new File("logs"));
        logSystem.setLogLevel(Level.ALL);
        LOGGER = logSystem.getLogger();
        StemBoxApp.LOGGER.CONFIG(StemBoxApp.class.getSimpleName() + " load frame...");
        new StemBoxApp();
    }


    private void registerShutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ledControl.disable();
            voiceManager.disable();
            stemLinkVoiceManager.disable();
            stemLinkManager.disable();
        }));
    }

}
