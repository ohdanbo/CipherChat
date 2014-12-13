import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

public class Login extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JLabel username,address;
	private JTextField userField, addressField;
	public static String userStr;
	double version = 1;

	public void checkVersion() throws Exception {
		URL website = new URL("http://www.ohdanbo.com/cipherchat/version.txt");
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream("C:\\Users\\Dan\\Desktop\\version.txt");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		
		BufferedReader readFile = new BufferedReader(new FileReader("C:\\Users\\Dan\\Desktop\\version.txt"));
		int versionStr = readFile.read();
		System.out.println("version.txt downloaded, string contains" + versionStr);
		readFile.close();
		if(versionStr == version) {
			//JOptionPane.showMessageDialog(null, "Up to date!", "CipherUpdater", JOptionPane.INFORMATION_MESSAGE);
		} else if (versionStr > version) {
			if (JOptionPane.showConfirmDialog(null, "New version available! Would you like to download?", "CipherUpdater", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			    downloadUpdate();
			} else {
			    return;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Ahead of remote file", "CipherUpdater", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void downloadUpdate() throws Exception {
		URL website = new URL("http://www.ohdanbo.com/cipherchat/cipherchat.exe");
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\cipherchat.exe");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		Runtime.getRuntime().exec("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\cipherchat.exe", null, new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\"));
		System.exit(0);
	}
	
	public Login() {
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception e1) {e1.printStackTrace();}
		//try{checkVersion();}catch(Exception e){e.printStackTrace();}
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(279, 75);
		panel.setBackground(Color.BLACK);
		
		username = new JLabel("Username: ".toUpperCase());
		username.setBounds(12, 10, 273, 25);
		username.setForeground(Color.GREEN);
		username.setFont(Resources.font());
		panel.add(username);

		address = new JLabel("Address: ".toUpperCase());
		address.setBounds(12,40, 273, 25);
		address.setForeground(Color.GREEN);
		address.setFont(Resources.font());
		panel.add(address);
		
		
		DocumentFilter filter = new Resources.Uppercase();
		
		userField = new JTextField();
		((AbstractDocument) userField.getDocument()).setDocumentFilter(filter);
		userField.setBackground(Color.BLACK);
		userField.setForeground(Color.GREEN);
		userField.setBorder(null);
		userField.addActionListener(this);
		userField.setFont(Resources.font());
		userField.setCaretColor(Color.BLACK);
		userField.setBounds(90,10,175,25);
		userField.requestFocus();
		panel.add(userField);
		
		addressField = new JTextField();
		((AbstractDocument) addressField.getDocument()).setDocumentFilter(filter);
		addressField.setBackground(Color.BLACK);
		addressField.setForeground(Color.GREEN);
		addressField.setBorder(null);
		addressField.addActionListener(this);
		addressField.setFont(Resources.font());
		addressField.setCaretColor(Color.BLACK);
		addressField.setBounds(90,40,175,25);
		addressField.requestFocus();
		panel.add(addressField);

		setTitle("Choose a username...");
		setSize(273, 75);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setUndecorated(true);
		setResizable(false);
		setVisible(true);
		add(panel);
	}
	
	public static void main(String[] args) {
		new Login();
	}
	
	private void login(String name, String address, int port) {
		dispose();
		new ClientWindow(name, address.toLowerCase(), port);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == userField) {
			addressField.requestFocus();
		}
		if(e.getSource() == addressField) {
			String name = userField.getText();
			String address = addressField.getText();
			if (address.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please enter an address to connect to", "Error!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int port = 8192;
			login(name, address, port);
		}
	}
}
