import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttCallback;

public class PIPP {

	private JFrame frame;
	private JTextField textuser;
	private JTextField texthost;
	private JTextField textport;
	private JTextField textid;
	private JTextField textparola;
	private JTextField texttext;

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
		
		JLabel titlu = new JLabel("LA O BERE");
		titlu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		titlu.setBounds(217, 25, 125, 20);
		frame.getContentPane().add(titlu);
		titlu.setVisible(false);
		
		JLabel halba = new JLabel("Se umple halba . . .");
		halba.setFont(new Font("Tahoma", Font.PLAIN, 20));
		halba.setBounds(168, 287, 174, 38);
		frame.getContentPane().add(halba);
		halba.setVisible(false);
		
		JLabel user = new JLabel("Username");
		user.setBounds(55, 67, 76, 29);
		frame.getContentPane().add(user);
		user.setVisible(false);
		
		textuser = new JTextField();
		textuser.setBounds(154, 67, 286, 28);
		frame.getContentPane().add(textuser);
		textuser.setColumns(10);
		textuser.setVisible(false);
		
		JLabel host = new JLabel("Host");
		host.setBounds(55, 68, 62, 27);
		frame.getContentPane().add(host);
		
		JLabel port = new JLabel("Port");
		port.setBounds(55, 112, 61, 38);
		frame.getContentPane().add(port);
		
		JLabel id = new JLabel("ClientID");
		id.setBounds(55, 164, 54, 29);
		frame.getContentPane().add(id);
		
		texthost = new JTextField();
		texthost.setBounds(154, 67, 349, 29);
		frame.getContentPane().add(texthost);
		texthost.setColumns(10);
		
		textport = new JTextField();
		textport.setBounds(154, 118, 139, 27);
		frame.getContentPane().add(textport);
		textport.setColumns(10);
		
		textid = new JTextField();
		textid.setBounds(154, 164, 237, 29);
		frame.getContentPane().add(textid);
		textid.setColumns(10);
		
		JLabel parola = new JLabel("Topic");
		parola.setBounds(54, 117, 62, 29);
		frame.getContentPane().add(parola);
		parola.setVisible(false);
		
		textparola = new JTextField();
		textparola.setBounds(154, 117, 286, 29);
		frame.getContentPane().add(textparola);
		textparola.setColumns(10);
		textparola.setVisible(false);
		
		JTextArea textchat = new JTextArea();
		textchat.setBounds(41, 53, 367, 348);
		frame.getContentPane().add(textchat);
		textchat.setVisible(false);
		textchat.setEditable(false);
		
		JTextArea textonline = new JTextArea();
		textonline.setBounds(436, 53, 114, 348);
		frame.getContentPane().add(textonline);
		textonline.setVisible(false);
		textonline.setEditable(false);
		
		texttext = new JTextField();
		texttext.setBounds(41, 412, 367, 65);
		frame.getContentPane().add(texttext);
		texttext.setColumns(10);
		texttext.setVisible(false);
		
		JButton adauga = new JButton("Adauga");
		adauga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String topic        = textparola.getText();
				String content      = texttext.getText();
				int qos             = 2;
				String port = textport.getText();
				String broker = "tcp://" + texthost.getText() + ":" + port;
				String clientId     = textid.getText();
				MemoryPersistence persistence = new MemoryPersistence();  

				try {			
				    MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
				    MqttConnectOptions connOpts = new MqttConnectOptions();
				    System.out.println("Connecting to broker: "+broker);
				    sampleClient.connect(connOpts);
				    System.out.println("Connected");
				    System.out.println("Publishing message: "+content);
				    MqttMessage message = new MqttMessage(content.getBytes());
				    message.setQos(qos);
				    sampleClient.publish(topic, message);
				    System.out.println("Message published");
				    //sampleClient.setCallback(this);
				    textchat.append(textid.getText()+ " : " + texttext.getText()+ "\n");
				} catch(MqttException me) {
					textchat.append("ERROR : reason "+me.getReasonCode() + "\n");
					textchat.append("ERROR : msg "+me.getMessage() + "\n");
					textchat.append("ERROR : loc "+me.getLocalizedMessage() + "\n");
					textchat.append("ERROR : cause "+me.getCause() + "\n");
					textchat.append("ERROR : excep "+me);
				    me.printStackTrace();
				}
				texttext.setText(null);
			}
		});
		adauga.setBounds(436, 412, 114, 65);
		frame.getContentPane().add(adauga);
		adauga.setVisible(false);
		
		JButton login = new JButton("Connect");
		login.setBounds(261, 227, 108, 38);
		frame.getContentPane().add(login);
		login.setVisible(false);
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String nume = textuser.getText();
				System.out.println(nume + " Alo, buna dimineata");
				halba.setVisible(true);
				textonline.append(nume);
				textchat.setVisible(true);
				texttext.setVisible(true);
				textonline.setVisible(true);
				adauga.setVisible(true);
				user.setVisible(false);
				textuser.setVisible(false);
				parola.setVisible(false);
				textparola.setVisible(false);
				login.setVisible(false);
				halba.setVisible(false);
				
				
			}
		});
		
		
		JButton back = new JButton("Back");
		JButton next = new JButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				texthost.setVisible(false);
				textport.setVisible(false);
				textid.setVisible(false);
				host.setVisible(false);
				port.setVisible(false);
				id.setVisible(false);
				back.setVisible(true);
				textparola.setVisible(true);
				parola.setVisible(true);
				user.setVisible(true);
				textuser.setVisible(true);
				next.setVisible(false);
				login.setVisible(true);
				
			}
		});
		next.setBounds(217, 227, 106, 38);
		frame.getContentPane().add(next);
		
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				texthost.setVisible(true);
				textport.setVisible(true);
				textid.setVisible(true);
				host.setVisible(true);
				port.setVisible(true);
				id.setVisible(true);
				back.setVisible(false);
				textparola.setVisible(false);
				parola.setVisible(false);
				user.setVisible(false);
				textuser.setVisible(false);
				next.setVisible(true);
				login.setVisible(false);
				
			}
		});
		back.setBounds(144, 227, 106, 38);
		frame.getContentPane().add(back);
	
		back.setVisible(false);		
	}
}
