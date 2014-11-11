import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MessageBox extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public MessageBox(String title, String message, String type) {
		setTitle(title);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception e1) {e1.printStackTrace();}
		setBounds(100, 100, 280, 130);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{162, 109, 0};
		gbl_contentPanel.rowHeights = new int[]{114, 33, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel = new JLabel(message);
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.gridheight = 2;
			gbc_lblNewLabel.gridwidth = 2;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			GridBagConstraints gbc_buttonPane = new GridBagConstraints();
			gbc_buttonPane.gridwidth = 2;
			gbc_buttonPane.insets = new Insets(0, 0, 5, 5);
			gbc_buttonPane.gridx = 0;
			gbc_buttonPane.gridy = 2;
			contentPanel.add(buttonPane, gbc_buttonPane);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				JButton okButton = new JButton("Yes");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("No");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

}
