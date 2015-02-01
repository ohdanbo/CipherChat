package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginLinux extends JFrame {
	
	private JTextField usernameFld, addressFld;
	
	public LoginLinux() {
		JPanel p = new JPanel();
		p.setLayout(null);
		p.setSize(280,130);
		
		JLabel usernameLbl = new JLabel("Username: ");
		usernameLbl.setBounds(10,10,usernameLbl.getPreferredSize().width, usernameLbl.getPreferredSize().height);
		p.add(usernameLbl);
		
		JLabel addressLbl = new JLabel("IP Address: ");
		addressLbl.setBounds(10,40,addressLbl.getPreferredSize().width, addressLbl.getPreferredSize().height);
		p.add(addressLbl);
		
		usernameFld = new JTextField();
		usernameFld.setBounds(10 + usernameLbl.getPreferredSize().width + 10, 9, 179,25);
		usernameFld.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addressFld.requestFocus();
			}
		});
		p.add(usernameFld);
		
		addressFld = new JTextField();
		addressFld.setBounds(10 + usernameLbl.getPreferredSize().width + 10, 39, 179,25);
		addressFld.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processInformation();
			}
		});
		p.add(addressFld);
		
		JButton connectBtn = new JButton("Connect");
		connectBtn.setBounds(280 - 10 - connectBtn.getPreferredSize().width, 130-connectBtn.getPreferredSize().height-33, connectBtn.getPreferredSize().width, connectBtn.getPreferredSize().height);
		connectBtn.setFocusable(false);
		connectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processInformation();
			}
		});
		p.add(connectBtn);
		
		setTitle("CipherChat - Login");
		setSize(280,130);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		add(p);
	}
	
	public static void login(String name, String address, int port) {
		new ClientWindow(name, address.toLowerCase(), port);
	}
	
	private void processInformation() {
		if (!usernameFld.getText().equals("") && !addressFld.getText().equals("")) {
			String name = usernameFld.getText();
			String address = addressFld.getText();
			int port = 8192;
			dispose();
			login(name, address, port);
		} else {
			JOptionPane.showMessageDialog(null,"Please fill requred text fields!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
}
