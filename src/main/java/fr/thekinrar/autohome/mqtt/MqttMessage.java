package fr.thekinrar.autohome.mqtt;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import fr.thekinrar.autohome.AutoHome;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

@SuppressWarnings("unchecked")
public abstract class MqttMessage<T extends MqttMessage<T>> {
    private final AutoHome autoHome;
    private String topic;
    private byte[] payload;

    protected MqttMessage(AutoHome autoHome) {
        this.autoHome = autoHome;
    }

    public T topic(String... topic) {
        this.topic = "zigbee2mqtt/" + String.join("/", topic);
        return (T) this;
    }

    public T payload(byte[] payload) {
        this.payload = payload;
        return (T) this;
    }

    public T payload(String payload) {
        return payload(payload.getBytes(StandardCharsets.UTF_8));
    }

    public T payload(JsonElement json) {
        return payload(new Gson().toJson(json));
    }

    public void publish() {
        LoggerFactory.getLogger("MQTT").debug("Publishing: " + topic + " <= " + new String(payload));

        try {
            autoHome.getMqtt().publish(topic, payload, 0, false);
        } catch (MqttException e) {
            LoggerFactory.getLogger("MQTT").error("Publish failed", e);
        }
    }
}
