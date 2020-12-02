package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.io.File;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
import utility.WordChecker;

@SuppressWarnings("serial")
public class RegisPage extends JFrame implements ActionListener{

	JTextField tfEmail, tfUsername, tfName;
	JLabel errorMsg;
	JPasswordField pfPass, pfCpass;

	JButton regisBtn, loginBtn;

	public RegisPage() {
		try {
			this.setIconImage(ImageIO.read(new File("assets/icon256.png")));
		} catch (Exception e) {
		}
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setTitle("Register AnemoShop");
		setSize(450, 450);

		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		initializeAll();
	}

	private void initializeAll() {
		JPanel regis = new JPanel(new BorderLayout());
		JPanel title = initializeTitle();
		JPanel content = initializeContent();

		content.setBackground(Color.decode("#dcdcff"));

		regis.add(title, BorderLayout.NORTH);
		regis.add(content, BorderLayout.CENTER);

		add(regis);

	}

	private JPanel initializeTitle() {
		JPanel panel = new JPanel();

		panel.setBackground(Color.decode("#dcdcff"));

		JLabel title = new JLabel("AnemoShop Register");
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

		JLabel email, pass, cPass, name, username;
		email = new JLabel("Email: ");
		pass = new JLabel("Password: ");
		cPass = new JLabel("Confirm Password");
		name = new JLabel("Name");
		username = new JLabel("Username");
		errorMsg = new JLabel();
		errorMsg.setPreferredSize(new Dimension(450, 30));

		email.setFont(new Font(Main.FONT_NAME, Main.FONT_STYLE, Main.FONT_SIZE_CONTENT));
		pass.setFont(new Font(Main.FONT_NAME, Main.FONT_STYLE, Main.FONT_SIZE_CONTENT));
		cPass.setFont(new Font(Main.FONT_NAME, Main.FONT_STYLE, Main.FONT_SIZE_CONTENT));
		name.setFont(new Font(Main.FONT_NAME, Main.FONT_STYLE, Main.FONT_SIZE_CONTENT));
		username.setFont(new Font(Main.FONT_NAME, Main.FONT_STYLE, Main.FONT_SIZE_CONTENT));

		tfEmail = new JTextField();
		pfPass = new JPasswordField();
		pfCpass = new JPasswordField();
		tfName = new JTextField();
		tfUsername = new JTextField();
		KeyAdapter keyAdapter = new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				String fUsername = tfUsername.getText();
				if (fUsername.length() < 5 || fUsername.length() > 30) {
					errorMsg.setText("");
					return;
				}
				if(!WordChecker.checkAlphabet(fUsername)){
					errorMsg.setText("Username must only contain Alphabet");
					errorMsg.setForeground(Color.RED);
					return;
				}
				Vector<User> vUser = Main.getvUser();
				for (int i = 0; i < vUser.size(); i++) {
					User user2 = (User) vUser.get(i);
					if (fUsername.equals(user2.getUsername())) {
						errorMsg.setText("Username has been registered");
						errorMsg.setForeground(Color.RED);
						return;
					}
				}
				errorMsg.setText("Username can be used");
				errorMsg.setForeground(Color.BLACK);
			
			}
		};
		
		tfUsername.addKeyListener(keyAdapter);

		JPanel pnl = new JPanel(new GridLayout(5, 2, 15, 25));
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		pnl.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		pnl.setBackground(Color.decode("#dcdcff"));
		p1.setBackground(Color.decode("#dcdcff"));

		pnl.add(email);
		pnl.add(tfEmail);
		pnl.add(pass);
		pnl.add(pfPass);
		pnl.add(cPass);
		pnl.add(pfCpass);
		pnl.add(name);
		pnl.add(tfName);
		pnl.add(username);
		pnl.add(tfUsername);

		p1.add(pnl);
		p1.add(errorMsg);

		return p1;
	}

	public void refreshTable() {
		this.getContentPane().removeAll();
		initializeAll();
		this.revalidate();
		this.repaint();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JPanel initializeBtn() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));

		regisBtn = new JButton("Register");
		loginBtn = new JButton("Have an account? Login here");

		loginBtn.setBorderPainted(false);
		loginBtn.setFocusPainted(false);
		loginBtn.setContentAreaFilled(false);

		loginBtn.setBackground(Color.decode("#c7fbaa"));
		regisBtn.setBackground(Color.decode("#c7fbaa"));

		loginBtn.setFont(new Font(Main.FONT_NAME, Main.FONT_STYLE, 12));
		loginBtn.setForeground(Color.BLUE);
		regisBtn.setFont(new Font(Main.FONT_NAME, Main.FONT_STYLE, Main.FONT_SIZE_CONTENT));

		Font original = loginBtn.getFont();
		Map attributes = original.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		loginBtn.setFont(original.deriveFont(attributes));
		loginBtn.setHorizontalAlignment(SwingConstants.RIGHT);

		regisBtn.setPreferredSize(new Dimension(100, 30));
		loginBtn.setPreferredSize(new Dimension(450, 30));
		;

		loginBtn.addActionListener(this);
		regisBtn.addActionListener(this);

		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(loginBtn);

		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(regisBtn);

		panel.setBackground(Color.decode("#dcdcff"));
		p1.setBackground(Color.decode("#dcdcff"));
		p2.setBackground(Color.decode("#dcdcff"));

		panel.add(p2);
		panel.add(p1);

		return panel;
	}

	public void moveToPageLogin() {

		new LoginPage();
		this.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == loginBtn) {

			String fEmail = tfEmail.getText();
			@SuppressWarnings("deprecation")
			String fPass = pfPass.getText();
			@SuppressWarnings("deprecation")
			String fCpass = pfCpass.getText();
			String fName = tfName.getText();
			String fUsername = tfUsername.getText();
			if (!fEmail.isEmpty() || !fPass.isEmpty() || !fCpass.isEmpty() || !fName.isEmpty()
					|| !fUsername.isEmpty()) {
				int jawab = JOptionPane.showConfirmDialog(this, "Are you sure want back to Login?");
				if (jawab != JOptionPane.YES_OPTION) {
					return;
				}
			}

			moveToPageLogin();
			return;
		} else if (e.getSource() == regisBtn) {
			String fEmail = tfEmail.getText();
			@SuppressWarnings("deprecation")
			String fPass = pfPass.getText();
			@SuppressWarnings("deprecation")
			String fCpass = pfCpass.getText();
			String fName = tfName.getText();
			String fUsername = tfUsername.getText();

			if (fEmail.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Email must be filled", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			} else if (fEmail.contains(" ")) {
				JOptionPane.showMessageDialog(null, "Email can't contain space", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else if (!fEmail.contains("@") || fEmail.indexOf("@") != fEmail.lastIndexOf("@")) {
				JOptionPane.showMessageDialog(null, "Email must only have one '@'", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			} else if (fEmail.startsWith("@")) {
				JOptionPane.showMessageDialog(null, "Email can't start with '@'", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else if (fEmail.contains("@.") || fEmail.contains(".@")) {
				JOptionPane.showMessageDialog(null, "Email \".\" must not be next to \"@\"", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else if (!fEmail.endsWith(".com")) {
				JOptionPane.showMessageDialog(null, "Email must be ended with '.com'", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else if (fPass.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Password must be filled", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else if (fPass.length() < 5 || fPass.length() > 30) {
				JOptionPane.showMessageDialog(null, "Password's Length must between 5 and 30 characters", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else if (fPass.contains(" ")) {
				JOptionPane.showMessageDialog(null, "Password can't contain space", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else if (!WordChecker.checkAlphanum(fPass)) {
				JOptionPane.showMessageDialog(null, "Password must only contain Alphabet and Number\nand at least one Alphabet and one Number", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else if (fCpass.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Confirm Password must be filled", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else if (!fCpass.contentEquals(fPass)) {
				JOptionPane.showMessageDialog(null, "Confirm Password must be matched with password", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else if (fName.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Name must be filled", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else if (!fName.trim().contains(" ")) {
				JOptionPane.showMessageDialog(null, "Name must at least 2 words", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else if (fUsername.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Username must be filled", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else if (fUsername.length() < 5 || fUsername.length() > 30) {
				JOptionPane.showMessageDialog(null, "Username's Length must between 5 and 30 characters", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else if (!WordChecker.checkAlphabet(fUsername)) {
				JOptionPane.showMessageDialog(null, "Username must only contain characters", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else {

				Vector<User> vUser = Main.getvUser();
				for (int i = 0; i < vUser.size(); i++) {
					User user = (User) vUser.get(i);

					if (fEmail.equals(user.getEmail())) {
						JOptionPane.showMessageDialog(this, "Email has been registered", "Warning",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (fUsername.equals(user.getUsername())) {
						JOptionPane.showMessageDialog(this, "Username has been registered", "Warning",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				Main.addUser(new User(fName, fUsername, fEmail, fPass));

				moveToPageLogin();
			}

		}

	}

}
