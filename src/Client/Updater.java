package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Updater {
	
	int currentVersion = 11; //change this everytime a new stable version is published
	//will push the update to everyone that is on older version.
	int remoteVersion;
	
	public Updater() {
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception e1) {e1.printStackTrace();}
		try {
			checkForUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkForUpdate() throws Exception {
		URL url = new URL("http://ohdanbo.com/f/version.txt");
		Scanner s = new Scanner(url.openStream());
		int newVersion = s.nextInt();
		if(currentVersion < newVersion) {
			askForUpdate();
		} else {
			if(System.getProperty("os.name").contains("Windows")) {
				new Login();
			} else {
				new LoginLinux();
			}
		}
	}
	
	public void askForUpdate() {
		if(!System.getProperty("os.name").contains("Windows")) {
			JPanel p = new JPanel();
			p.setLayout(null);
			p.setSize(400,250);
			
			JFrame f = new JFrame();
			f.setTitle("Update available!");
			f.setSize(400,110);
			f.setLocationRelativeTo(null);
			f.setIconImage(new ImageIcon(getClass().getResource("icon.png")).getImage());
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setResizable(false);
			
			JLabel ask = new JLabel("There is an update available, would you like to download it?");
			ask.setBounds(10,10,(int)ask.getPreferredSize().width, (int)ask.getPreferredSize().height);
			p.add(ask);
			
			JButton update = new JButton("Download update");
			update.setBounds(90,40,(int)update.getPreferredSize().width+15, (int) update.getPreferredSize().height);
			update.setFocusable(false);
			update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
					URL website = new URL("http://ohdanbo.com/f/cipherchat.jar");
					ReadableByteChannel rbc = Channels.newChannel(website.openStream());
					FileOutputStream fos = new FileOutputStream("//home//" + System.getProperty("user.name") + "//Desktop//newcipherchat.jar");
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
					} catch (Exception ex) {ex.printStackTrace();}
					JOptionPane.showMessageDialog(null,"Success!", "Update has been downloaded!", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			p.add(update);
			
			JButton cancel = new JButton("Cancel");
			cancel.setBounds(110+(int)update.getPreferredSize().width,40,(int)cancel.getPreferredSize().width+15, (int) cancel.getPreferredSize().height);
			cancel.setFocusable(false);
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			p.add(cancel);
			f.add(p);
			f.setVisible(true);
		} else {
			JPanel p = new JPanel();
			p.setLayout(null);
			p.setSize(320,100);
			
			JFrame f = new JFrame();
			f.setTitle("Update available!");
			f.setSize(320,100);
			f.setLocationRelativeTo(null);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setResizable(false);
			
			JLabel ask = new JLabel("There is an update available, would you like to download it?");
			ask.setBounds(15,10,(int)ask.getPreferredSize().width, (int)ask.getPreferredSize().height);
			p.add(ask);
			
			JButton update = new JButton("Download update");
			update.setBounds(55,40,(int)update.getPreferredSize().width, (int) update.getPreferredSize().height);
			update.setFocusable(false);
			update.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
					URL website = new URL("http://ohdanbo.com/f/cipherchat.jar");
					ReadableByteChannel rbc = Channels.newChannel(website.openStream());
					File file = new File("C://Users//" + System.getProperty("user.name") + "//Documents//CipherChat//");
					file.mkdirs();
					FileOutputStream fos = new FileOutputStream("C://Users//" + System.getProperty("user.name") + "//Documents//CipherChat//CipherChat.jar");
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
					} catch (Exception ex) {ex.printStackTrace();}
					JOptionPane.showMessageDialog(null,"Success!", "Update has been downloaded!", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			p.add(update);
			
			JButton cancel = new JButton("Cancel");
			cancel.setBounds(60+(int)update.getPreferredSize().width,40,(int)cancel.getPreferredSize().width, (int) cancel.getPreferredSize().height);
			cancel.setFocusable(false);
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			p.add(cancel);
			f.add(p);
			f.setVisible(true);
		}
	}
		
	public static void main(String[] args) { 
		new Updater();
	}
}
