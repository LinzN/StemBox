package de.linzn.stemBoxClient.led;

import de.linzn.raspberrypiApa102.LEDManager;
import de.linzn.raspberrypiApa102.ledData.LED;
import de.linzn.raspberrypiApa102.ledData.LEDRing;
import de.linzn.raspberrypiApa102.rings.PulseRing;
import de.linzn.raspberrypiApa102.rings.RotatingRing;
import de.linzn.stemBoxClient.StemBoxApp;

public class LEDControl {

    private final StemBoxApp stemBoxApp;
    private final LEDManager ledManager;
    private LEDModeEnum mode;

    public LEDControl(StemBoxApp stemBoxApp) {
        this.stemBoxApp = stemBoxApp;
        this.mode = LEDModeEnum.NONE;
        this.ledManager = new LEDManager();
        this.setupIdleLED();
        this.setupErrorLED();
        this.setupRecordingLED();
        this.setupSpeakingLED();
    }

    public void enable() {
        this.ledManager.init(12);
    }

    public void disable() {
        this.ledManager.setLEDMode(LEDModeEnum.NONE.name());
    }

    public void updateMode(LEDModeEnum ledModeEnum) {
        if (this.mode != ledModeEnum) {
            this.mode = ledModeEnum;
            this.ledManager.setLEDMode(ledModeEnum.name());
        }
    }

    private void setupSpeakingLED() {
        LEDRing ledRing = new RotatingRing(12);
        ledRing.setLED(0, new LED(0, 255, 0));
        ledRing.setLED(1, new LED(0, 255, 0));
        ledRing.setLED(2, new LED(0, 255, 0));
        ledRing.setLED(3, new LED(0, 255, 0));
        ledRing.setLED(4, new LED(100, 100, 100));
        ledRing.setLED(5, new LED(100, 100, 100));
        ledRing.setLED(6, new LED(100, 100, 100));
        ledRing.setLED(7, new LED(100, 100, 100));
        ledRing.setLED(8, new LED(100, 100, 100));
        ledRing.setLED(9, new LED(100, 100, 100));
        ledRing.setLED(10, new LED(100, 100, 100));
        ledRing.setLED(11, new LED(100, 100, 100));
        this.ledManager.addLEDRing(LEDModeEnum.SPEAKING.name(), ledRing);
    }

    private void setupRecordingLED() {
        LEDRing ledRing = new RotatingRing(12);
        ledRing.setLED(0, new LED(0, 240, 150));
        ledRing.setLED(1, new LED(0, 0, 255));
        ledRing.setLED(2, new LED(0, 0, 200));
        ledRing.setLED(3, new LED(0, 240, 150));
        ledRing.setLED(4, new LED(100, 100, 100));
        ledRing.setLED(5, new LED(100, 100, 100));
        ledRing.setLED(6, new LED(0, 240, 150));
        ledRing.setLED(7, new LED(0, 0, 255));
        ledRing.setLED(8, new LED(0, 0, 255));
        ledRing.setLED(9, new LED(0, 240, 150));
        ledRing.setLED(10, new LED(100, 100, 100));
        ledRing.setLED(11, new LED(100, 100, 100));
        this.ledManager.addLEDRing(LEDModeEnum.RECORDING.name(), ledRing);
    }

    private void setupErrorLED() {
        LEDRing ledRing = new PulseRing(12);
        ledRing.setLED(0, new LED(255, 0, 0));
        ledRing.setLED(1, new LED(255, 0, 0));
        ledRing.setLED(2, new LED(255, 0, 0));
        ledRing.setLED(3, new LED(255, 0, 0));
        ledRing.setLED(4, new LED(255, 0, 0));
        ledRing.setLED(5, new LED(255, 0, 0));
        ledRing.setLED(6, new LED(255, 0, 0));
        ledRing.setLED(7, new LED(255, 0, 0));
        ledRing.setLED(8, new LED(255, 0, 0));
        ledRing.setLED(9, new LED(255, 0, 0));
        ledRing.setLED(10, new LED(255, 0, 0));
        ledRing.setLED(11, new LED(255, 0, 0));
        this.ledManager.addLEDRing(LEDModeEnum.ERROR.name(), ledRing);
    }

    private void setupIdleLED() {
        LEDRing ledRing = new RotatingRing(12);
        ledRing.setLED(0, new LED(0, 0, 0));
        ledRing.setLED(1, new LED(0, 0, 0));
        ledRing.setLED(2, new LED(0, 0, 0));
        ledRing.setLED(3, new LED(0, 0, 0));
        ledRing.setLED(4, new LED(0, 0, 0));
        ledRing.setLED(5, new LED(0, 0, 0));
        ledRing.setLED(6, new LED(0, 0, 0));
        ledRing.setLED(7, new LED(0, 0, 0));
        ledRing.setLED(8, new LED(0, 0, 0));
        ledRing.setLED(9, new LED(0, 0, 0));
        ledRing.setLED(10, new LED(0, 0, 0));
        ledRing.setLED(11, new LED(0, 0, 0));
        this.ledManager.addLEDRing(LEDModeEnum.NONE.name(), ledRing);
    }
}
