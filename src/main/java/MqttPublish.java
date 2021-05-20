//package application.Pole;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttPublish implements MqttCallback {

    MqttClient client;

    public MqttPublish() {
    }

    public static void main(String[] args) {
        new MqttPublish().doDemo();
    }

    public void doDemo() {
        try {
            client = new MqttClient("tcp://broker.mqttdashboard.com", "clientId-62XDoQt42Z");
            client.connect();
            client.setCallback(this);
            client.subscribe("TopicForTest");
            MqttMessage message = new MqttMessage();
            message.setPayload("Message from Eclipse to localhost broker on topic : TopicForTest".getBytes());
            client.publish("TopicForTest", message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        // TODO Auto-generated method stub

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub

    }

}