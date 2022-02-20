package fr.thekinrar.autohome;

import com.google.gson.JsonObject;

public abstract class Device {
    private final AutoHome autoHome;
    private final String name;

    public Device(AutoHome autoHome, String name) {
        this.autoHome = autoHome;
        this.name = name;

        autoHome.addDevice(this);
    }

    public AutoHome getAutoHome() {
        return autoHome;
    }

    public String getName() {
        return name;
    }

    public abstract void onRawAction(String action);
    public abstract void onRawState(JsonObject state);
}
