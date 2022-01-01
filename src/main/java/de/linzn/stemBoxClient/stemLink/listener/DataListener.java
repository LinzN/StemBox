package de.linzn.stemBoxClient.stemLink.listener;


import de.linzn.stemBoxClient.StemBoxApp;
import de.linzn.stemBoxClient.led.LEDModeEnum;
import de.linzn.stemLink.components.events.ReceiveDataEvent;
import de.linzn.stemLink.components.events.handler.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class DataListener {

    @EventHandler(channel = "stembox_client")
    public void onData(ReceiveDataEvent event) {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getDataInBytes()));
        try {
            StemBoxApp.LOGGER.INFO("STEMLink Message arrived!");
            String subChannel = in.readUTF();

            String status = in.readUTF();

            if (subChannel.equalsIgnoreCase("update-box-status")) {
                if (status.equalsIgnoreCase("listening")) {
                    StemBoxApp.LOGGER.CONFIG("Set stemBox status to listening");
                    StemBoxApp.stemBoxApp.ledControl.updateMode(LEDModeEnum.RECORDING);
                } else if (status.equalsIgnoreCase("speaking")) {
                    StemBoxApp.LOGGER.CONFIG("Set stemBox to speaking");
                    StemBoxApp.stemBoxApp.ledControl.updateMode(LEDModeEnum.SPEAKING);
                } else if (status.equalsIgnoreCase("idle")) {
                    StemBoxApp.LOGGER.CONFIG("Set stemBox to idle");
                    StemBoxApp.stemBoxApp.ledControl.updateMode(LEDModeEnum.NONE);
                }
            }

        } catch (IOException e) {
            StemBoxApp.LOGGER.ERROR(e);
        }
    }
}
