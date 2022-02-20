package fr.thekinrar.autohome.devices;

import com.google.gson.JsonObject;
import fr.thekinrar.autohome.AutoHome;
import fr.thekinrar.autohome.Device;
import fr.thekinrar.autohome.DeviceListener;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class Rotator extends Device {
    public Rotator(AutoHome autoHome, String name) {
        super(autoHome, name);
    }

    @Override
    public void onRawAction(String raw) {
        try {
            getAutoHome().callListeners(Rotator.class, (Listener listener) ->
                    listener.onAction(this, Action.valueOf(raw.toUpperCase(Locale.ROOT))));
        } catch(IllegalArgumentException e) {
            LoggerFactory.getLogger("Device/Rotator").warn("Action " + raw + " unknown");
        }
    }

    @Override
    public void onRawState(JsonObject state) {}

    public enum Action {
        PLAY_PAUSE,
        ROTATE_RIGHT,
        ROTATE_LEFT,
        ROTATE_STOP
    }

    public static abstract class Listener implements DeviceListener<Rotator, Action> {
        @Override
        public final Class<Rotator> getDeviceClass() {
            return Rotator.class;
        }
    }
}
