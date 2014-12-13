package Client;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DocumentFilter;

public class ClientWindow extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField txtMessage;
	private JTextArea history;
	private DefaultCaret caret;
	private Thread run, listen;
	private Client client;
	private boolean running = false;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmOnlineUsers;
	private JMenuItem mntmExit;
	private JList list, usersOnline;
	private String username, messageContents;
	private int mouseX, mouseY;
	private OnlineUsers users;

	public ClientWindow(String name, String address, int port) {
		client = new Client(name, address, port);
		boolean connect = client.openConnection(address);
		if (!connect) {
			System.err.println("Connection failed!");
			console("Connection failed!");
		}
		createWindow();
		console(("Attempting a connection to " + address + ":" + port + ", user: " + name).toUpperCase());
		String connection = "/c/" + name + "/e/";
		client.send(connection.getBytes());
		users = new OnlineUsers();
		running = true;
		run = new Thread(this, "Running");
		run.start();
	}

	private void createWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(960, 540);
		setUndecorated(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setBackground(new Color(0, 0, 0, 0));

		contentPane = new JPanel();
		contentPane.setSize(960, 540);
		contentPane.setOpaque(false);
		contentPane.setLayout(null);
		setContentPane(contentPane);

		history = new JTextArea();
		history.setBounds(11, 50, 750, 400);
		history.setBackground(Color.BLACK);
		history.setForeground(Color.GREEN);
		history.setEditable(false);
		history.setFocusable(false);
		DefaultCaret caret = (DefaultCaret) history.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		history.setFont(Resources.font());
		JScrollPane jsp = new JScrollPane(history);
		jsp.getViewport().setBackground(Color.BLACK);
		jsp.setBorder(BorderFactory.createEmptyBorder());
		jsp.setBounds(11, 50, 750, 422);
		contentPane.add(jsp);

		final JLabel exit = new JLabel();
		exit.setIcon(new ImageIcon(getClass().getResource("closeBtn.png")));
		exit.setSize(25, 24);
		exit.setForeground(Color.GREEN);
		exit.setLocation(960 - 10 - 25, 44 / 2 - 25 / 2 + 2);
		exit.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				String disconnect = "/d/" + client.getID() + "/e/";
				send(disconnect, false);
				running = false;
				client.close();
				System.exit(0);
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		});
		contentPane.add(exit);

		final JLabel min = new JLabel();
		min.setIcon(new ImageIcon(getClass().getResource("minBtn.png")));
		min.setSize(25, 24);
		min.setForeground(Color.GREEN);
		min.setLocation(960 - 10 - 25 - 5 - 25, 44 / 2 - 25 / 2 + 2);
		min.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				setExtendedState(ICONIFIED);
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		});
		contentPane.add(min);

		final JLabel dragFrame = new JLabel();
		dragFrame.setBounds(0, 0, 960, 45);
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
		contentPane.add(dragFrame);

		list = new JList();
		list.setBounds(780, 73, 500, 100);
		list.setFont(Resources.font());
		list.setForeground(Color.WHITE);
		list.setBackground(Color.GREEN);
		JScrollPane p = new JScrollPane();
		p.setViewportView(list);
		contentPane.add(p);

		usersOnline = new JList();
		usersOnline.setBounds(778, 65, 200, 100);
		usersOnline.setBackground(Color.BLACK);
		usersOnline.setForeground(Color.GREEN);
		usersOnline.setCellRenderer(new DefaultListCellRenderer() {

			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, false, false);

				return this;
			}
		});
		usersOnline.setFont(Resources.font());
		// usersOnline.setText("- Danbo".toUpperCase());
		contentPane.add(usersOnline);

		JLabel l = new JLabel(new ImageIcon(getClass().getResource("gui.png")));
		l.setBounds(0, 0, 960, 540);
		contentPane.add(l);

		DocumentFilter filter = new Resources.Uppercase();
		txtMessage = new JTextField();
		((AbstractDocument) txtMessage.getDocument()).setDocumentFilter(filter);
		txtMessage.setBounds(34, 495, 725, 30);
		txtMessage.setForeground(Color.GREEN);
		txtMessage.setBackground(Color.BLACK);
		txtMessage.setBorder(null);
		txtMessage.setCaretColor(Color.BLACK);
		txtMessage.setFocusable(true);
		txtMessage.requestFocus();
		txtMessage.setFont(Resources.font());
		txtMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send(txtMessage.getText(), true);
			}
		});
		contentPane.add(txtMessage);

		setVisible(true);

		txtMessage.requestFocusInWindow();
	}

	public void run() {
		listen();
	}

	private void send(String message, boolean text) {
		if (message.startsWith("/")) {
			switch (message.toLowerCase()) {
			case "/help":
				console(("Commands are as follows:" + " \n  /help - Show all available commands" + " \n  /changeusername username - Changes username to whatever you enter" + " \n  /sendfile filename.png - Send file to all online users" + " \n  /whosonline - Shows a list of users online" + " \n  /call username - Voicecall specified user if online" + " \n  /clear - Clears messages" + " \n  /exit - Exit application").toUpperCase());
				break;
			case "/exit":
				String disconnect = "/d/" + client.getID() + "/e/";
				send(disconnect, false);
				running = false;
				client.close();
				System.exit(0);
			case "/changeusername":
				console("coming soon".toUpperCase());
				break;
			case "/sendfile":
				console("coming soon".toUpperCase());
				break;
			case "/call":
				console("coming soon".toUpperCase());
				break;
			case "/clear":
				history.setText("");
				break;
			case "/whosonline":
				break;
			default:
				break;
			}
		}
		if (message.equals(""))
			return;
		if (text) {
			message = client.getName() + ": " + message;
			message = "/m/" + message + "/e/";
			txtMessage.setText("");
		}
		client.send(message.getBytes());
	}

	public void update(String[] users) {
		list.setListData(users);
		usersOnline.setListData(users);
	}

	public void listen() {
		listen = new Thread("Listen") {
			public void run() {
				while (running) {
					String message = client.receive();
					if (message.startsWith("/c/")) {
						client.setID(Integer.parseInt(message.split("/c/|/e/")[1]));
						console(("Successfully connected to server! ID: " + client.getID()).toUpperCase());
					} else if (message.startsWith("/m/")) {
						String text = message.substring(3);
						text = text.split("/e/")[0];
						console(text);
					} else if (message.startsWith("/i/")) {
						String text = "/i/" + client.getID() + "/e/";
						send(text, false);
					} else if (message.startsWith("/u/")) {
						String[] u = message.split("/u/|/n/|/e/");
						update(Arrays.copyOfRange(u, 1, u.length - 1));
					}
				}
			}
		};
		listen.start();
	}

	public void console(String message) {
		history.append(message + "\n\r");
		history.setCaretPosition(history.getDocument().getLength());
	}
}
