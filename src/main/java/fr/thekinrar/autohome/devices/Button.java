package fr.thekinrar.autohome.devices;

import com.google.gson.JsonObject;
import fr.thekinrar.autohome.AutoHome;
import fr.thekinrar.autohome.Device;
import fr.thekinrar.autohome.DeviceListener;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class Button extends Device {
    public Button(AutoHome autoHome, String name) {
        super(autoHome, name);
    }

    @Override
    public void onRawAction(String raw) {
        try {
            getAutoHome().callListeners(Button.class, (Listener listener) ->
                    listener.onAction(this, Action.valueOf(raw.toUpperCase(Locale.ROOT))));
        } catch(IllegalArgumentException e) {
            LoggerFactory.getLogger("Device/Button").warn("Action " + raw + " unknown");
        }
    }

    @Override
    public void onRawState(JsonObject state) {}

    public enum Action {
        TOGGLE,
        BRIGHTNESS_UP_CLICK,
        BRIGHTNESS_UP_HOLD,
        BRIGHTNESS_UP_RELEASE,
        BRIGHTNESS_DOWN_CLICK,
        BRIGHTNESS_DOWN_HOLD,
        BRIGHTNESS_DOWN_RELEASE,
        ARROW_LEFT_CLICK,
        ARROW_LEFT_HOLD,
        ARROW_LEFT_RELEASE,
        ARROW_RIGHT_CLICK,
        ARROW_RIGHT_HOLD,
        ARROW_RIGHT_RELEASE
    }

    public static abstract class Listener implements DeviceListener<Button, Action> {
        @Override
        public final Class<Button> getDeviceClass() {
            return Button.class;
        }
    }
}
