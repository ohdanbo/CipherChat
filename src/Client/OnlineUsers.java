package Client;


import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class OnlineUsers extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JList list;

	public OnlineUsers() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Online Users");
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);

		list = new JList();
		JScrollPane p = new JScrollPane();
		p.setViewportView(list);
		contentPane.add(p);
	}

	public void update(String[] users) {
		list.setListData(users);
	}

}