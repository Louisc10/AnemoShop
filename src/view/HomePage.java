package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import model.User;

@SuppressWarnings("serial")
public class HomePage extends JFrame implements ActionListener {
	Toolkit tk;
	User user;
	JMenu menu;

	private static HomePage instance = null;

	public static HomePage getInstance() {
		if (instance == null) {
			synchronized (HomePage.class) {
				if (instance == null) {
					instance = new HomePage();
				}
			}
		}
		return instance;
	}

	private void removeInstance() {
		if (instance != null) {
			synchronized (HomePage.class) {
				if (instance != null) {
					instance = null;
				}
			}
		}
	}

	JDesktopPane dp = new JDesktopPane() {
		private Image image;
		{
			try {
				image = ImageIO.read(new File("assets/background.jpg"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		}
	};
	ShowAllItem showAllItem = null;
	ViewCart viewCarts = null;
	ShowHistory showHistory = null;
	JMenuItem showItem, viewCart, historyCart, logOut, closeApp;

	public void setUser(User user) {
		this.user = user;
		showAllItem = new ShowAllItem(user);
		dp.add(showAllItem);
		Dimension desktopSize = dp.getSize();
		Dimension jInternalFrameSize = showAllItem.getSize();
		showAllItem.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
				(desktopSize.height - jInternalFrameSize.height) / 2);
	}

	private HomePage() {
		tk = Toolkit.getDefaultToolkit();
		int width = (int) tk.getScreenSize().getWidth();
		int heigth = (int) tk.getScreenSize().getHeight();

		this.setSize(width, heigth);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);

		initializeAll();

		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("AnemoShop");

		setResizable(false);
		setLocationRelativeTo(null);

	}

	private void initializeAll() {
		initializeMenuBar();
		this.add(dp);
	}

	private void initializeMenuBar() {
		JMenuBar mb = new JMenuBar();
		menu = new JMenu("Menu");
		showItem = new JMenuItem("Show All Item");
		viewCart = new JMenuItem("My Cart");
		historyCart = new JMenuItem("My Transaction History");
		logOut = new JMenuItem("Log Out");
		closeApp = new JMenuItem("Close");
		showItem.setIcon(new ImageIcon("assets/item25.png"));
		viewCart.setIcon(new ImageIcon("assets/cart25.png"));
		historyCart.setIcon(new ImageIcon("assets/history25.png"));
		logOut.setIcon(new ImageIcon("assets/logout25.png"));
		closeApp.setIcon(new ImageIcon("assets/close25.png"));
		showItem.addActionListener(this);
		viewCart.addActionListener(this);
		historyCart.addActionListener(this);
		logOut.addActionListener(this);
		closeApp.addActionListener(this);
		showItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		viewCart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.ALT_MASK));
		historyCart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
		logOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		closeApp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		menu.add(showItem);
		menu.add(viewCart);
		menu.add(historyCart);
		menu.add(new JSeparator());
		menu.add(logOut);
		menu.add(closeApp);
		mb.add(menu);

		setJMenuBar(mb);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == showItem) {
			if (showAllItem == null || showAllItem.isClosed()) {
				showAllItem = new ShowAllItem(user);
				dp.add(showAllItem);
				Dimension desktopSize = dp.getSize();
				Dimension jInternalFrameSize = showAllItem.getSize();
				showAllItem.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
						(desktopSize.height - jInternalFrameSize.height) / 2);
			}
			try {
				showAllItem.setSelected(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (e.getSource() == viewCart) {
			if (viewCarts == null || viewCarts.isClosed()) {
				viewCarts = new ViewCart(user);
				dp.add(viewCarts);
				Dimension desktopSize = dp.getSize();
				Dimension jInternalFrameSize = viewCarts.getSize();
				viewCarts.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
						(desktopSize.height - jInternalFrameSize.height) / 2);
			}
			try {
				viewCarts.setSelected(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == historyCart) {
			if (showHistory == null || showHistory.isClosed()) {
				showHistory = new ShowHistory(user);
				dp.add(showHistory);
				Dimension desktopSize = dp.getSize();
				Dimension jInternalFrameSize = showHistory.getSize();
				showHistory.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
						(desktopSize.height - jInternalFrameSize.height) / 2);
			}
			try {
				showHistory.setSelected(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (e.getSource() == logOut) {
			int jawab = JOptionPane.showConfirmDialog(this, "Are You sure want to Logout?");
			if (jawab == JOptionPane.YES_OPTION) {
				this.removeInstance();
				moveToPageLogin();
			}
		} else if (e.getSource() == closeApp) {
			int jawab = JOptionPane.showConfirmDialog(this, "Close the App?");
			if (jawab == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(this, "Good Bye <3");
				this.dispose();
			}
		}
	}

	public void moveToPageLogin() {
		new LoginPage();
		this.dispose();
	}
}
