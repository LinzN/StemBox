package de.linzn.stemBoxClient.stemLink;


import de.linzn.stemBoxClient.StemBoxApp;
import de.linzn.stemLink.components.IStemLinkWrapper;

import java.util.concurrent.Executors;
import java.util.logging.Level;

public class StemLinkTerminalWrapper implements IStemLinkWrapper {
    @Override
    public void runThread(Runnable runnable) {
        Executors.newSingleThreadExecutor().submit(runnable);
    }

    @Override
    public void log(Object s, Level level) {
        if (level == Level.INFO) {
            StemBoxApp.LOGGER.INFO(s);
        } else if (level == Level.FINE) {
            StemBoxApp.LOGGER.DEBUG(s);
        } else if (level == Level.WARNING) {
            StemBoxApp.LOGGER.WARNING(s);
        } else if (level == Level.CONFIG) {
            StemBoxApp.LOGGER.CONFIG(s);
        } else if (level == Level.SEVERE) {
            StemBoxApp.LOGGER.ERROR(s);
        } else {
            StemBoxApp.LOGGER.DEBUG(s);
        }

    }

}
