package fr.thekinrar.autohome.devices;

import com.google.gson.JsonObject;
import fr.thekinrar.autohome.AutoHome;
import fr.thekinrar.autohome.Device;
import fr.thekinrar.autohome.mqtt.JsonMqttMessage;

public class Bulb extends Device {
    private boolean lightState;
    private double brightness;
    private double colorTemp;

    public Bulb(AutoHome autoHome, String name) {
        super(autoHome, name);
    }

    @Override
    public void onRawAction(String action) {}

    @Override
    public void onRawState(JsonObject state) {
        lightState = state.get("state").getAsString().equals("ON");
        brightness = state.get("brightness").getAsDouble() / 254;
        colorTemp = (state.get("color_temp").getAsDouble() - 250) / 204;
    }

    public void setLightState(boolean state) {
        new JsonMqttMessage(getAutoHome())
                .topic(getName(), "set")
                .put("state", state ? "ON" : "OFF")
                .publish();
    }

    public void switchOn() {
        setLightState(true);
    }

    public void switchOff() {
        setLightState(false);
    }

    public void toggleLightState() {
        new JsonMqttMessage(getAutoHome())
                .topic(getName(), "set")
                .put("state", "TOGGLE")
                .publish();
    }

    public boolean getLightState() {
        return lightState;
    }

    public void setBrightness(double brightness) {
        new JsonMqttMessage(getAutoHome())
                .topic(getName(), "set")
                .put("brightness", (int) (brightness * 254))
                .publish();
    }

    public double getBrightness() {
        return brightness;
    }

    public void setColorTemp(double colorTemp) {
        new JsonMqttMessage(getAutoHome())
                .topic(getName(), "set")
                .put("color_temp", (int) ((colorTemp * 204) + 250))
                .publish();
    }

    public double getColorTemp() {
        return colorTemp;
    }

    public void setBrightnessAndTemp(double brightness, double temp) {
        setBrightness(brightness);
        setColorTemp(temp);
    }
}
