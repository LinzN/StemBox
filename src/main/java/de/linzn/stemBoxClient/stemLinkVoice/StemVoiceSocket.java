package de.linzn.stemBoxClient.stemLinkVoice;

import de.linzn.stemBoxClient.StemBoxApp;
import de.linzn.stemLink.components.encryption.DataHead;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.Executors;


public class StemVoiceSocket implements Runnable {

    private final String host;
    private final int port;
    private Socket socket;
    private boolean keepAlive;
    private boolean allowSend;

    public StemVoiceSocket() {
        StemBoxApp.LOGGER.CONFIG("Start StemLinkLiteConnection");
        this.socket = new Socket();
        this.host = StemBoxApp.stemBoxApp.appConfiguration.stemLinkVoiceHost;
        this.port = StemBoxApp.stemBoxApp.appConfiguration.stemLinkVoicePort;
        this.allowSend = false;
    }

    public void enable() {
        this.keepAlive = true;
        StemBoxApp.LOGGER.CONFIG("Enable StemLinkLiteConnection...");
        Executors.newSingleThreadExecutor().submit(this);
    }

    public void disable() {
        this.keepAlive = false;
        this.closeConnection();
    }

    @Override
    public void run() {

        while (keepAlive) {
            try {
                this.socket = new Socket(host, port);
                this.socket.setTcpNoDelay(true);
                StemBoxApp.LOGGER.CONFIG("Stem voice client connected to server!");
                allowSend = false;
                write_stemBox_data();

                while (this.isValidConnection()) {
                    this.readInput();
                }

            } catch (IOException e2) {
                this.closeConnection();
            }
            /* Reduce cpu usage while trying to reconnect to stemLink server */
            try {
                Thread.sleep(250);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void closeConnection() {
        if (!this.socket.isClosed() && this.socket.getRemoteSocketAddress() != null) {
            try {
                allowSend = false;
                StemBoxApp.LOGGER.CONFIG("Stem voice client connection closed!");
                this.socket.close();
            } catch (IOException ignored) {
            }
        }
    }


    public boolean isValidConnection() {
        return this.socket.isConnected() && !this.socket.isClosed();
    }


    private void write_stemBox_data() {
        try {
            BufferedOutputStream bOutStream = new BufferedOutputStream(this.socket.getOutputStream());
            DataOutputStream dataOut = new DataOutputStream(bOutStream);
            dataOut.writeUTF("stemBox_uuid");
            dataOut.writeUTF(StemBoxApp.stemBoxApp.appConfiguration.stemBoxUUID.toString());
            bOutStream.flush();
            StemBoxApp.LOGGER.CONFIG("Send stemBox_uuid to server");
            allowSend = true;
        } catch (IOException ignored) {
        }
    }

    public void writeData(byte[] b, int off, int len) {
        if (this.isValidConnection() && allowSend) {
            try {
                BufferedOutputStream bOutStream = new BufferedOutputStream(this.socket.getOutputStream());
                DataOutputStream dataOut = new DataOutputStream(bOutStream);
                dataOut.write(b, off, len);
                bOutStream.flush();
            } catch (IOException e) {
                StemBoxApp.LOGGER.ERROR("Is already closed!");
                closeConnection();
            }
        }
    }

    public void readInput() throws IOException {
        BufferedInputStream bInStream = new BufferedInputStream(this.socket.getInputStream());
        DataInputStream dataInput = new DataInputStream(bInStream);

        DataHead dataHead = DataHead.fromString(new String(dataInput.readUTF().getBytes()));
        String headerChannel = dataHead.getHeader();
        int dataSize = dataHead.getDataSize();
        byte[] dataPackage = new byte[dataSize];

        for (int i = 0; i < dataSize; i++) {
            dataPackage[i] = dataInput.readByte();
        }

        StemBoxApp.LOGGER.CONFIG("Receiving dataPackage header: " + headerChannel + " size: " + dataSize);
        StemBoxApp.LOGGER.CONFIG(Arrays.toString(dataPackage));
    }
}
