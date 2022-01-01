package de.linzn.stemBoxClient.stemLink.listener;


import de.linzn.stemBoxClient.StemBoxApp;
import de.linzn.stemLink.components.events.ConnectEvent;
import de.linzn.stemLink.components.events.DisconnectEvent;
import de.linzn.stemLink.components.events.handler.EventHandler;

public class ConnectionListener {

    @EventHandler
    public void onConnect(final ConnectEvent event) {
        StemBoxApp.LOGGER.INFO("StemLink connected to server");
    }

    @EventHandler
    public void onDisconnect(final DisconnectEvent event) {
        StemBoxApp.LOGGER.INFO("StemLink disconnected from server");
    }

}
