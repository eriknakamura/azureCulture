package azureCulture;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.SwingConstants;
import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class mainInterface {

	private JFrame frame;
	private JTextField txtEnterIp;
	private Socket armControlRotate;
	private Socket armControlLift;
	private Socket temperature;
	private Socket screen;
	private JLabel lblCelsius;
	
	OutputStream armRotateStream;
	DataOutputStream armRotateDOS;
	OutputStream armLiftStream;
	DataOutputStream armLiftDOS;
	
	InputStream temperatureStream;
	DataInputStream tempStreamDIS;
	
	double tempMeasured = 0;
	double armRotate = 0;
	double armLift = 0;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainInterface window = new mainInterface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public mainInterface() throws UnknownHostException, IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	private void initialize() throws UnknownHostException, IOException {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 269, 200, 151);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblSensorInfo = new JLabel("Sensor Info");
		lblSensorInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSensorInfo.setBounds(60, 11, 73, 14);
		panel.add(lblSensorInfo);
		
		JLabel lblWifi = new JLabel("WiFi:");
		lblWifi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWifi.setBounds(10, 36, 73, 14);
		panel.add(lblWifi);
		
		JLabel lblNewLabel = new JLabel("Moisture:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 61, 73, 14);
		panel.add(lblNewLabel);
		
		JLabel lblTemperature = new JLabel("Temperature:");
		lblTemperature.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTemperature.setBounds(0, 86, 83, 14);
		panel.add(lblTemperature);
		
		JLabel lblWind = new JLabel("Wind:");
		lblWind.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWind.setBounds(10, 111, 73, 14);
		panel.add(lblWind);
		
		JLabel lblNewLabel_1 = new JLabel("WiFi Signal");
		lblNewLabel_1.setBounds(93, 36, 97, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("moisture in ppm");
		lblNewLabel_2.setBounds(93, 61, 97, 14);
		panel.add(lblNewLabel_2);

		lblCelsius = new JLabel("Fahrenheit");
		lblCelsius.setBounds(93, 86, 97, 14);
		panel.add(lblCelsius);
		
		JLabel lblMph = new JLabel("mph");
		lblMph.setBounds(93, 111, 97, 14);
		panel.add(lblMph);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(314, 120, 300, 300);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblLocation = new JLabel("Arm Control");
		lblLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblLocation.setBounds(121, 11, 62, 14);
		panel_1.add(lblLocation);
		
		JSlider slider = new JSlider();

		slider.setValue(0);
		slider.setMinimum(-100);
		slider.setBounds(51, 72, 200, 26);
		panel_1.add(slider);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				armRotate = slider.getValue();
				try {
					initSockets();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		JLabel lblRotate = new JLabel("Rotate");
		lblRotate.setHorizontalAlignment(SwingConstants.CENTER);
		lblRotate.setBounds(126, 47, 46, 14);
		panel_1.add(lblRotate);
		
		JSlider slider_1 = new JSlider();

		slider_1.setValue(0);
		slider_1.setMinimum(-100);
		slider_1.setBounds(51, 202, 200, 26);
		panel_1.add(slider_1);
		slider_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				armLift = slider_1.getValue();
				try {
					initSockets();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		JLabel lblLift = new JLabel("Lift");
		lblLift.setHorizontalAlignment(SwingConstants.CENTER);
		lblLift.setBounds(126, 177, 46, 14);
		panel_1.add(lblLift);
		
		JLabel lblAzureculture = new JLabel("AgrowBot Client");
		lblAzureculture.setFont(new Font("Leelawadee UI Semilight", Font.PLAIN, 55));
		lblAzureculture.setHorizontalAlignment(SwingConstants.CENTER);
		lblAzureculture.setBounds(10, 11, 604, 98);
		frame.getContentPane().add(lblAzureculture);
		
		txtEnterIp = new JTextField();
		txtEnterIp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txtEnterIp.setText("");
			}
		});
		txtEnterIp.setText("Enter IP");
		txtEnterIp.setBounds(10, 120, 130, 20);
		frame.getContentPane().add(txtEnterIp);
		txtEnterIp.setColumns(10);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					initSockets();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnConnect.setBounds(150, 119, 89, 23);
		frame.getContentPane().add(btnConnect);
		
		JLabel lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setBounds(10, 100, 130, 14);
		frame.getContentPane().add(lblIpAddress);
	}

	protected void initSockets() throws UnknownHostException, IOException {
		
		armControlRotate = new Socket(txtEnterIp.getText(), 81);
		armRotateStream = armControlRotate.getOutputStream();
		armRotateDOS = new DataOutputStream(armRotateStream);
		
		armControlLift = new Socket(txtEnterIp.getText(), 82);
		armLiftStream = armControlLift.getOutputStream();
		armLiftDOS = new DataOutputStream(armLiftStream);
		
		temperature = new Socket(txtEnterIp.getText(), 83);
		temperatureStream = temperature.getInputStream();
		tempStreamDIS = new DataInputStream(temperatureStream);
		
		updateData();
	}

	private void updateData() throws IOException {
		armRotateDOS.writeDouble(armRotate);
		armLiftDOS.writeDouble(armLift);
		tempMeasured = tempStreamDIS.readDouble();
		lblCelsius.setText(tempMeasured + " Fahrenheit");
	}
}
