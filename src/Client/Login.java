package Client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JTextField userField, addressField;
	private int mouseX, mouseY;
	
	public Login() {
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception ex) {ex.printStackTrace();}
		JPanel p = new JPanel();
		p.setSize(280,122);
		p.setLayout(null);
		p.setOpaque(false);
		
		JLabel exitbtn = new JLabel(new ImageIcon(getClass().getResource("/exitbutton.png")));
		exitbtn.setBounds(260,5,15,15);
		exitbtn.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		p.add(exitbtn);
		
		final JLabel dragFrame = new JLabel();
		dragFrame.setBounds(0, 0, 280, 35);
		dragFrame.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
			public void mouseMoved(MouseEvent arg0) {
			}
		});
		dragFrame.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
			public void mouseReleased(MouseEvent arg0) {
			}
		});
		p.add(dragFrame);
		
		userField = new JTextField();
		userField.setBounds(105,38,155,18);
		userField.setBorder(null);
		userField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addressField.requestFocus();
			}
		});
		p.add(userField);
		
		addressField = new JTextField();
		addressField.setBounds(105,65,155,18);
		addressField.setBorder(null);
		addressField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processInformation();
			}
		});
		p.add(addressField);
		
		JLabel label = new JLabel(new ImageIcon(getClass().getResource("/connect.png")));
		label.setBounds(183,90,88,23);
		label.setForeground(Color.WHITE);
		label.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
				processInformation();
			}
		});
		p.add(label);
		
		JLabel gui = new JLabel(new ImageIcon(getClass().getResource("/login-gui.png")));
		gui.setBounds(0,0,280,122);
		gui.setBackground(new Color(0,0,0,0));
		p.add(gui);
		
		setSize(280,122);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		setVisible(true);
		add(p);
	}
	
	public static void login(String name, String address, int port) {
		new ClientWindow(name, address.toLowerCase(), port);
	}
	
	private void processInformation() {
		if (!userField.getText().equals("") && !addressField.getText().equals("")) {
			String name = userField.getText();
			String address = addressField.getText();
			int port = 8192;
			dispose();
			login(name, address, port);
		} else {
			JOptionPane.showMessageDialog(null,"Please fill requred text fields!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
}
