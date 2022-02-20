package fr.thekinrar.autohome.devices;

import com.google.gson.JsonObject;
import fr.thekinrar.autohome.AutoHome;
import fr.thekinrar.autohome.Device;
import fr.thekinrar.autohome.mqtt.JsonMqttMessage;

public class Relay extends Device {
    private boolean state;

    public Relay(AutoHome autoHome, String name) {
        super(autoHome, name);
    }

    @Override
    public void onRawAction(String action) {}

    @Override
    public void onRawState(JsonObject json) {
        state = json.get("state").getAsString().equals("ON");
    }

    public void setState(boolean state) {
        new JsonMqttMessage(getAutoHome())
                .topic(getName(), "set")
                .put("state", state ? "ON" : "OFF")
                .publish();
    }

    public void switchOn() {
        setState(true);
    }

    public void switchOff() {
        setState(false);
    }

    public void toggleState() {
        new JsonMqttMessage(getAutoHome())
                .topic(getName(), "set")
                .put("state", "TOGGLE")
                .publish();
    }

    public boolean getState() {
        return state;
    }
}
