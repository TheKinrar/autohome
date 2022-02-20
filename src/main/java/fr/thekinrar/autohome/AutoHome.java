package fr.thekinrar.autohome;

import fr.thekinrar.autohome.mqtt.MqttListener;
import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class AutoHome {
    private MqttAsyncClient mqtt;
    private String mqttUri = "tcp://localhost:1883";
    private String mqttClientId = "autohome";
    private String mqttBaseTopic = "zigbee2mqtt";

    private final Map<String, Device> devices = new HashMap<>();
    private final Map<Class<? extends Device>, List<DeviceListener<?, ?>>> listeners = new HashMap<>();

    public void start() throws MqttException {
        mqtt = new MqttAsyncClient(mqttUri, mqttClientId);
        mqtt.setCallback(new MqttListener(this));
        mqtt.connect().waitForCompletion();
        mqtt.subscribe(mqttBaseTopic + "/#", 0).waitForCompletion();

        LoggerFactory.getLogger("AutoHome").info("AutoHome started");
    }

    public void setMqttUri(String mqttUri) {
        this.mqttUri = mqttUri;
    }

    public void setMqttClientId(String mqttClientId) {
        this.mqttClientId = mqttClientId;
    }

    public void setMqttBaseTopic(String mqttBaseTopic) {
        this.mqttBaseTopic = mqttBaseTopic;
    }

    public MqttAsyncClient getMqtt() {
        return mqtt;
    }

    public Group allDevices() {
        return allDevices("All devices");
    }

    public Group allDevices(String name) {
        return new Group(name, devices.values().stream().toList());
    }

    public Device getDevice(String name) {
        return devices.get(name);
    }

    public void addDevice(Device device) {
        devices.put(device.getName(), device);
    }

    public void addListener(DeviceListener<?, ?> listener) {
        listeners.computeIfAbsent(listener.getDeviceClass(), c -> new ArrayList<>()).add(listener);
    }

    public <T extends DeviceListener<?, ?>> void callListeners(Class<?> clazz, Consumer<?> consumer) {
        var list = listeners.get(clazz);
        if(list != null) {
            list.forEach((Consumer<? super DeviceListener<?, ?>>) consumer);
        }
    }
}
