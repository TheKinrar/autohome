package fr.thekinrar.autohome.devices;

import com.google.gson.JsonObject;
import fr.thekinrar.autohome.AutoHome;
import fr.thekinrar.autohome.Device;
import fr.thekinrar.autohome.DeviceListener;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class Switch extends Device {
    public Switch(AutoHome autoHome, String name) {
        super(autoHome, name);
    }

    @Override
    public void onRawAction(String raw) {
        try {
            getAutoHome().callListeners(Switch.class, (Switch.Listener listener) ->
                    listener.onAction(this, Switch.Action.valueOf(raw.toUpperCase(Locale.ROOT))));
        } catch(IllegalArgumentException e) {
            LoggerFactory.getLogger("Device/Switch").warn("Action " + raw + " unknown");
        }
    }

    @Override
    public void onRawState(JsonObject state) {}

    public enum Action {
        ON,
        OFF,
        BRIGHTNESS_UP,
        BRIGHTNESS_DOWN,
        BRIGHTNESS_STOP
    }

    public static abstract class Listener implements DeviceListener<Switch, Switch.Action> {
        @Override
        public final Class<Switch> getDeviceClass() {
            return Switch.class;
        }
    }
}
