package pack;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//BEGIN ######################### HiveMQTT Broker Imports ################################

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;

//END ######################### HiveMQTT Broker Imports ################################

public class PIPP {

	private JFrame frame;
	private JTextField textuser;
	private JTextField texthost;
	private JTextField textparola;
	private JTextField textsubt;
	private JTextField textpubt;
	private JTextField texttext;
	int connected = 0;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PIPP window = new PIPP();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PIPP() {
		initialize();
		
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Window 1");
		frame.setBounds(100, 100, 589, 542);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel user = new JLabel("Username");
		user.setBounds(68, 195, 76, 29);
		frame.getContentPane().add(user);
		
		textuser = new JTextField();
		textuser.setBounds(154, 195, 286, 28);
		frame.getContentPane().add(textuser);
		textuser.setColumns(10);
		
		JLabel host = new JLabel("Host");
		host.setBounds(69, 76, 62, 27);
		frame.getContentPane().add(host);
		
		JLabel pubtopic = new JLabel("PubTopic");
		pubtopic.setBounds(70, 106, 61, 38);
		frame.getContentPane().add(pubtopic);
		
		JLabel subtopic = new JLabel("SubTopic");
		subtopic.setBounds(69, 155, 54, 29);
		frame.getContentPane().add(subtopic);
		
		texthost = new JTextField();
		texthost.setBounds(154, 75, 286, 29);
		frame.getContentPane().add(texthost);
		texthost.setColumns(10);
		
		textparola = new JTextField();
		textparola.setBounds(154, 234, 286, 27);
		frame.getContentPane().add(textparola);
		textparola.setColumns(10);
		
		textsubt = new JTextField();
		textsubt.setBounds(154, 155, 286, 29);
		frame.getContentPane().add(textsubt);
		textsubt.setColumns(10);
		
		JLabel parola = new JLabel("Password");
		parola.setBounds(68, 235, 62, 29);
		frame.getContentPane().add(parola);
		
		textpubt = new JTextField();
		textpubt.setBounds(154, 115, 286, 29);
		frame.getContentPane().add(textpubt);
		textpubt.setColumns(10);
		
		JTextArea textchat = new JTextArea();
		textchat.setBounds(41, 77, 490, 351);
		frame.getContentPane().add(textchat);
		textchat.setVisible(false);
		textchat.setEditable(false);
		
		JTextArea textonline = new JTextArea();
		textonline.setBounds(41, 37, 490, 29);
		frame.getContentPane().add(textonline);
		textonline.setVisible(false);
		textonline.setEditable(false);
		
		texttext = new JTextField();
		texttext.setBounds(41, 439, 385, 38);
		frame.getContentPane().add(texttext);
		texttext.setColumns(10);
		texttext.setVisible(false);
		
		JButton adauga = new JButton("Adauga");
		adauga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String broker = texthost.getText();
				final String username = textuser.getText();
				final String password = textparola.getText();
				final String pubtopic = textpubt.getText();
				final String subtopic = textsubt.getText();
				final String mesaj = texttext.getText();
				
		        final Mqtt5BlockingClient client = MqttClient.builder()
		                .useMqttVersion5()
		                .serverHost(broker)
		                .serverPort(8883)
		                .sslWithDefaultConfig()
		                .buildBlocking();

		        client.connectWith()
		                .simpleAuth()
		                .username(username)
		                .password(UTF_8.encode(password))
		                .applySimpleAuth()
		                .send();

		        client.subscribeWith()
		                .topicFilter(subtopic)
		                .send();

		        client.toAsync().publishes(ALL, publish -> {
		            textchat.append(" -> " + UTF_8.decode(publish.getPayload().get()) + "\n");
		            client.disconnect();
		        });

		        client.publishWith()
		                .topic(pubtopic)
		                .payload(UTF_8.encode(mesaj))
		                .send();
			        
				texttext.setText(null);
			}
		});
		adauga.setBounds(436, 439, 95, 38);
		frame.getContentPane().add(adauga);
		adauga.setVisible(false);
		
		JButton login = new JButton("Connect");
		login.setBounds(232, 272, 108, 38);
		frame.getContentPane().add(login);
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String nume = textuser.getText();
				textonline.append(nume);
				textchat.setVisible(true);
				texttext.setVisible(true);
				textonline.setVisible(true);
				adauga.setVisible(true);
				user.setVisible(false);
				textuser.setVisible(false);
				parola.setVisible(false);
				textpubt.setVisible(false);
				login.setVisible(false);
				host.setVisible(false);
				texthost.setVisible(false);
				pubtopic.setVisible(false);
				subtopic.setVisible(false);
				textsubt.setVisible(false);
				textparola.setVisible(false);		
				
			}
		});
	}
}
