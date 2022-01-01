package de.linzn.stemBoxClient.stemLink;


import de.linzn.stemBoxClient.StemBoxApp;
import de.linzn.stemBoxClient.stemLink.listener.ConnectionListener;
import de.linzn.stemBoxClient.stemLink.listener.DataListener;
import de.linzn.stemLink.components.encryption.CryptContainer;
import de.linzn.stemLink.connections.ClientType;
import de.linzn.stemLink.connections.client.ClientConnection;

import java.util.UUID;


public class StemLinkManager {
    public ClientConnection clientConnection;

    public StemLinkManager(StemBoxApp stemBoxApp) {
        UUID clientUUID = stemBoxApp.appConfiguration.stemBoxUUID;
        String host = stemBoxApp.appConfiguration.stemLinkHost;
        int port = stemBoxApp.appConfiguration.stemLinkPort;
        String cryptAESKey = stemBoxApp.appConfiguration.cryptAESKey;
        byte[] vector16B = stemBoxApp.appConfiguration.vector16B;
        CryptContainer cryptContainer = new CryptContainer(cryptAESKey, vector16B);

        this.clientConnection = new ClientConnection(host, port, clientUUID, ClientType.DEFAULT, new StemLinkTerminalWrapper(), cryptContainer);
        this.clientConnection.registerEvents(new ConnectionListener());
        this.clientConnection.registerEvents(new DataListener());
    }

    public void enable() {
        this.clientConnection.setEnable();
    }

    public void disable() {
        this.clientConnection.setDisable();
    }
}
