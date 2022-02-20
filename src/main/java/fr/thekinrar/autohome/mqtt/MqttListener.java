package fr.thekinrar.autohome.mqtt;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.thekinrar.autohome.AutoHome;
import fr.thekinrar.autohome.Device;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.slf4j.LoggerFactory;

public class MqttListener implements MqttCallback {
    private final AutoHome autoHome;

    public MqttListener(AutoHome autoHome) {
        this.autoHome = autoHome;
    }

    @Override
    public void disconnected(MqttDisconnectResponse disconnectResponse) {}

    @Override
    public void mqttErrorOccurred(MqttException exception) {}

    @Override
    public void messageArrived(String rawTopic, MqttMessage message) throws Exception {
        String[] topic = rawTopic.split("/");
        if(topic.length < 2) return;
        if(!topic[0].equals("zigbee2mqtt")) return;

        LoggerFactory.getLogger("MQTT").debug("Receiving: " + rawTopic + ": " + message);

        String deviceName = topic[1];
        String payload = new String(message.getPayload());

        if(!deviceName.equals("bridge")) {
            Device device = autoHome.getDevice(deviceName);

            if(device != null) {
                if(topic.length == 3) {
                    String method = topic[2];
                    LoggerFactory.getLogger("MQTT/Device").debug("Receiving: " + deviceName + " => " + method + ": " + payload);
                    if(method.equals("action")) {
                        device.onRawAction(payload);
                    }
                } else if(topic.length == 2) {
                    device.onRawState(new Gson().fromJson(payload, JsonObject.class));
                }
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttToken token) {}

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {}

    @Override
    public void authPacketArrived(int reasonCode, MqttProperties properties) {}
}
