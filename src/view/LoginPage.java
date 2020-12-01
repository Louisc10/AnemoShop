package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.User;
import program.Main;

@SuppressWarnings("serial")
public class LoginPage extends JFrame implements ActionListener {

	JTextField tfUsername;
	JPasswordField pfPass;

	JButton loginBtn, regisBtn;

	public LoginPage() {
		try {
			this.setIconImage(ImageIO.read(new File("assets/icon256.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		setSize(450, 250);

		initializeAll();

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Login AnemoShop");

		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void initializeAll() {
		JPanel login = new JPanel(new BorderLayout());
		JPanel title = initializeTitle();
		JPanel content = initializeContent();

		content.setBackground(Color.decode("#dcdcff"));

		login.add(title, BorderLayout.NORTH);
		login.add(content, BorderLayout.SOUTH);

		add(login);

	}

	private JPanel initializeTitle() {

		JPanel panel = new JPanel();

		panel.setBackground(Color.decode("#dcdcff"));

		JLabel title = new JLabel("AnemoShop Login");
		title.setFont(new Font(Main.FONT_NAME, Main.FONT_STYLE, Main.FONT_SIZE_TITLE));

		panel.add(title);

		return panel;
	}

	private JPanel initializeContent() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(initializeComponents(), BorderLayout.CENTER);
		panel.add(initializeBtn(), BorderLayout.SOUTH);

		return panel;
	}

	private JPanel initializeComponents() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		panel.setBackground(Color.decode("#dcdcff"));

		JLabel username, pass;
		username = new JLabel("Username: ");
		pass = new JLabel("Password: ");

		username.setFont(new Font(Main.FONT_NAME, Main.FONT_STYLE, Main.FONT_SIZE_CONTENT));
		pass.setFont(new Font(Main.FONT_NAME, Main.FONT_STYLE, Main.FONT_SIZE_CONTENT));

		tfUsername = new JTextField();
		pfPass = new JPasswordField();

		JPanel pnl = new JPanel(new GridLayout(2, 2, 5, 5));
		pnl.setBackground(Color.decode("#dcdcff"));

		pnl.add(username);
		pnl.add(tfUsername);
		pnl.add(pass);
		pnl.add(pfPass);

		panel.add(pnl, BorderLayout.CENTER);

		return panel;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JPanel initializeBtn() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));

		regisBtn = new JButton("Not have an account? Register here");
		loginBtn = new JButton("Login");

		regisBtn.setBorderPainted(false);
		regisBtn.setFocusPainted(false);
		regisBtn.setContentAreaFilled(false);
		
		loginBtn.setBackground(Main.THEME_COLOR);
		regisBtn.setBackground(Main.THEME_COLOR);

		loginBtn.setFont(new Font(Main.FONT_NAME, Main.FONT_STYLE, Main.FONT_SIZE_CONTENT));
		regisBtn.setFont(new Font(Main.FONT_NAME, Main.FONT_STYLE, 12));
		regisBtn.setForeground(Color.BLUE);
		
		Font original = regisBtn.getFont();
        Map attributes = original. getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        regisBtn.setFont(original.deriveFont(attributes));
        regisBtn.setHorizontalAlignment(SwingConstants.RIGHT);

		loginBtn.setPreferredSize(new Dimension(100, 30));
		regisBtn.setPreferredSize(new Dimension(450, 30));

		loginBtn.addActionListener(this);
		regisBtn.addActionListener(this);

		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(regisBtn);

		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(loginBtn);

		panel.setBackground(Color.decode("#dcdcff"));
		p1.setBackground(Color.decode("#dcdcff"));
		p2.setBackground(Color.decode("#dcdcff"));

		panel.add(p2);
		panel.add(p1);

		return panel;
	}

	public void moveToPageBuy(User user) {
		HomePage.getInstance().setUser(user);
		this.dispose();
	}

	public void moveToPageRegister() {
		new RegisPage();
		this.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == regisBtn) {
			moveToPageRegister();
		} else if (e.getSource() == loginBtn) {

			String username = tfUsername.getText();
			@SuppressWarnings("deprecation")
			String password = pfPass.getText();

			if (Main.getvUser().isEmpty()) {
				JOptionPane.showMessageDialog(null, "No user. Please register", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			} else {
				Vector<User> vUser = Main.getvUser();
				for (int i = 0; i < vUser.size(); i++) {
					User user = (User) vUser.get(i);

					if (username.equals(user.getUsername())) {
						if (password.equals(user.getPassword())) {
							moveToPageBuy(user);
							return;
						} else {
							JOptionPane.showMessageDialog(this, "Password is invalid", "Warning",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
				}
				JOptionPane.showMessageDialog(this, "User not found, please register", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

		}
	}

}
