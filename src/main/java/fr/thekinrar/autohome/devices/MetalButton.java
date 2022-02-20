package fr.thekinrar.autohome.devices;

import com.google.gson.JsonObject;
import fr.thekinrar.autohome.AutoHome;
import fr.thekinrar.autohome.Device;
import fr.thekinrar.autohome.DeviceListener;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class MetalButton extends Device {
    public MetalButton(AutoHome autoHome, String name) {
        super(autoHome, name);
    }

    @Override
    public void onRawAction(String raw) {
        try {
            getAutoHome().callListeners(MetalButton.class, (Listener listener) ->
                    listener.onAction(this, Action.valueOf(raw.toUpperCase(Locale.ROOT))));
        } catch(IllegalArgumentException e) {
            LoggerFactory.getLogger("Device/MetalButton").warn("Action " + raw + " unknown");
        }
    }

    @Override
    public void onRawState(JsonObject state) {}

    public enum Action {
        ON,
        OFF,
        BRIGHTNESS_MOVE_UP,
        BRIGHTNESS_MOVE_DOWN,
        BRIGHTNESS_STOP,
        ARROW_LEFT_CLICK,
        ARROW_LEFT_HOLD,
        ARROW_LEFT_RELEASE,
        ARROW_RIGHT_CLICK,
        ARROW_RIGHT_HOLD,
        ARROW_RIGHT_RELEASE
    }

    public static abstract class Listener implements DeviceListener<MetalButton, Action> {
        @Override
        public final Class<MetalButton> getDeviceClass() {
            return MetalButton.class;
        }
    }
}
