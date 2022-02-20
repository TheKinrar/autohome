package fr.thekinrar.autohome.mqtt;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.thekinrar.autohome.AutoHome;

public class JsonMqttMessage extends MqttMessage<JsonMqttMessage> {
    private final JsonObject json = new JsonObject();

    public JsonMqttMessage(AutoHome autoHome) {
        super(autoHome);
    }

    public JsonMqttMessage put(String key, JsonElement value) {
        json.add(key, value);
        return this;
    }

    public JsonMqttMessage put(String key, Boolean value) {
        json.addProperty(key, value);
        return this;
    }

    public JsonMqttMessage put(String key, Number value) {
        json.addProperty(key, value);
        return this;
    }

    public JsonMqttMessage put(String key, String value) {
        json.addProperty(key, value);
        return this;
    }

    public JsonMqttMessage put(String key, Character value) {
        json.addProperty(key, value);
        return this;
    }

    @Override
    public void publish() {
        payload(json);
        super.publish();
    }
}
