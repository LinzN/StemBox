package de.linzn.stemBoxClient;

import de.linzn.simplyConfiguration.FileConfiguration;
import de.linzn.simplyConfiguration.provider.YamlConfiguration;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;

public class AppConfiguration {


    private final String fileName = "config.yml";
    private final StemBoxApp stemBoxApp;
    public Level logLevel;
    public String stemLinkVoiceHost;
    public int stemLinkVoicePort;
    public String stemLinkHost;
    public int stemLinkPort;
    public String cryptAESKey;
    public byte[] vector16B;
    public UUID stemBoxUUID;
    /* Variables */
    private FileConfiguration configFile;

    /* Create class instance */
    public AppConfiguration(StemBoxApp stemBoxApp) {
        this.stemBoxApp = stemBoxApp;
        this.init();
    }

    private static byte[] toByteArray(String string) {
        String[] strings = string.replace("[", "").replace("]", "").split(", ");
        byte[] result = new byte[strings.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Byte.parseByte(strings[i]);
        }
        return result;
    }

    /* Load file*/
    public void init() {
        this.configFile = YamlConfiguration.loadConfiguration(new File(this.fileName));
        this.logLevel = Level.parse(this.configFile.getString("system.logLevel", Level.ALL.getName()));
        this.stemLinkVoiceHost = this.configFile.getString("voiceServer.host", "10.8.0.8");
        this.stemLinkVoicePort = this.configFile.getInt("voiceServer.port", 50000);

        this.stemLinkHost = this.configFile.getString("stemLink.host", "10.8.0.8");
        this.stemLinkPort = this.configFile.getInt("stemLink.port", 11102);
        this.cryptAESKey = this.configFile.getString("stemLink.crypt.cryptAESKey", "3979244226452948404D635166546A576D5A7134743777217A25432A462D4A61");
        this.vector16B = toByteArray(this.configFile.getString("stemLink.crypt.vector16B", Arrays.toString(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7})));

        this.stemBoxUUID = UUID.fromString(this.configFile.getString("stemBox.uuid", UUID.randomUUID().toString()));

        this.configFile.save();
    }

}
