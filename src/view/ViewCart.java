package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Cart;
import model.Item;
import model.User;
import program.Main;

@SuppressWarnings("serial")
public class ViewCart extends JInternalFrame implements MouseListener {

	private User activateUser;
	private Cart cartSelected;
	JScrollPane top;
	JPanel bottom;

	HashMap<JPanel, Cart> allCartPanel = new HashMap<>();
	Vector<JPanel> vCartPanel = new Vector<>();

	JTextField tfItemName, tfItemPrice, tfTotalPrice;
	JTextArea taItemDescription;
	JSpinner sItemQuantity;
	JButton updateBtn, buyBtn;

	public ViewCart(User user) {
		activateUser = user;
		try {
			this.setFrameIcon(new ImageIcon("assets/icon25.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		setTitle("My Cart");
		setVisible(true);

		setSize(515, 800);
		setMinimumSize(new Dimension(515, 800));
		setClosable(true);
		setIconifiable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		initializeAll();
	}

	private void initializeAll() {
		top = initalizeTopContent();
		bottom = initalizeBottomContent();

		this.add(top, BorderLayout.NORTH);
		this.add(bottom, BorderLayout.SOUTH);
	}

	private JPanel initalizeBottomContent() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(13, 1));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panel.add(new JLabel("Item Name"));
		tfItemName = new JTextField();
		tfItemName.setEditable(false);
		panel.add(tfItemName);

		panel.add(new JLabel("Item Price"));
		tfItemPrice = new JTextField();
		tfItemPrice.setEditable(false);
		panel.add(tfItemPrice);

		panel.add(new JLabel("Item Description"));
		taItemDescription = new JTextArea();
		taItemDescription.setEditable(false);
		taItemDescription.setLineWrap(true);
		taItemDescription.setWrapStyleWord(true);
		panel.add(taItemDescription);

		panel.add(new JLabel("Quantity"));
		SpinnerModel sm = new SpinnerNumberModel(0, 0, 100, 1);
		sItemQuantity = new JSpinner(sm);
		sItemQuantity.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (cartSelected != null) {
					try {
						int qty = Integer.parseInt(sItemQuantity.getValue().toString());
						long price = cartSelected.getItem().getPrice();
						long total = qty * price;
						tfTotalPrice.setText(total + " G");
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		panel.add(sItemQuantity);

		panel.add(new JLabel("Total Price"));
		tfTotalPrice = new JTextField();
		tfTotalPrice.setEditable(false);
		panel.add(tfTotalPrice);

		panel.add(new JLabel());
		updateBtn = new JButton("Update Item");
		updateBtn.addMouseListener(this);
		panel.add(updateBtn);

		buyBtn = new JButton("Buy all Item");
		buyBtn.addMouseListener(this);
		panel.add(buyBtn);

		return panel;
	}

	public void refreshTable() {
		this.getContentPane().removeAll();
		initializeAll();
		this.revalidate();
		this.repaint();
	}

	private JScrollPane initalizeTopContent() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		Vector<Cart> carts = Main.getvCartByUser(activateUser);
		for (Cart cart : carts) {
			JPanel content = initializeCartContent(cart);
			content.addMouseListener(this);
			allCartPanel.put(content, cart);
			vCartPanel.add(content);
			panel.add(content);
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(panel);
		scrollPane.setPreferredSize(new Dimension(500, 400));
		return scrollPane;
	}

	private JPanel initializeCartContent(Cart cart) {
		Item item = cart.getItem();
		int qty = cart.getQuantity();

		Border blackline = BorderFactory.createLineBorder(Color.black);
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setPreferredSize(new Dimension(500, 80));
		p1.setMaximumSize(new Dimension(500, 80));
		p1.setMinimumSize(new Dimension(500, 80));

		JPanel panel = new JPanel(new GridLayout(4, 1, 0, 0));
		panel.setPreferredSize(new Dimension(405, 80));
		panel.setMinimumSize(new Dimension(405, 80));
		panel.setMaximumSize(new Dimension(405, 80));

		JPanel name = new JPanel(new BorderLayout(2, 1));
		JLabel nameLbl = new JLabel("Name");
		nameLbl.setPreferredSize(new Dimension(75, 20));
		JLabel nameContent = new JLabel(": " + item.getName());
		nameContent.setPreferredSize(new Dimension(230, 20));
		name.add(nameLbl, BorderLayout.WEST);
		name.add(nameContent, BorderLayout.CENTER);
		panel.add(name);

		JPanel price = new JPanel(new BorderLayout(2, 1));
		JLabel priceLbl = new JLabel("Price");
		priceLbl.setPreferredSize(new Dimension(75, 20));
		JLabel priceContent = new JLabel(": " + item.getPrice() + " G");
		priceContent.setPreferredSize(new Dimension(230, 20));
		price.add(priceLbl, BorderLayout.WEST);
		price.add(priceContent, BorderLayout.CENTER);
		panel.add(price);

		JPanel description = new JPanel(new BorderLayout(2, 1));
		JLabel descriptionLbl = new JLabel("Description");
		descriptionLbl.setPreferredSize(new Dimension(75, 40));
		JLabel descriptionContent = new JLabel(": " + item.getDescription());
		descriptionContent.setPreferredSize(new Dimension(230, 40));
		description.add(descriptionLbl, BorderLayout.WEST);
		description.add(descriptionContent, BorderLayout.CENTER);
		panel.add(description);

		JPanel quantity = new JPanel(new BorderLayout(2, 1));
		JLabel quantityLbl = new JLabel("Quantity");
		quantityLbl.setPreferredSize(new Dimension(75, 20));
		JLabel quantityContent = new JLabel(": " + qty);
		quantityContent.setPreferredSize(new Dimension(425, 20));
		quantity.add(quantityLbl, BorderLayout.WEST);
		quantity.add(quantityContent, BorderLayout.CENTER);
		panel.add(quantity);

		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

		JPanel p2 = new JPanel();
		p2.setMinimumSize(new Dimension(80, 80));
		p2.setMaximumSize(new Dimension(80, 80));
		p2.setPreferredSize(new Dimension(80, 80));

		JLabel img = new JLabel(imageResizer("assets/prod/" + item.getImage(), 60));
		img.setMinimumSize(new Dimension(60, 60));
		img.setMaximumSize(new Dimension(60, 60));
		p2.add(img, BorderLayout.CENTER);

		p1.add(panel, BorderLayout.WEST);
		p1.add(p2, BorderLayout.CENTER);
		p1.setBorder(blackline);

		return p1;
	}

	private ImageIcon imageResizer(String path, int size) {
		ImageIcon imageIcon = new ImageIcon(path);
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == updateBtn) {
			int qty = Integer.parseInt(sItemQuantity.getValue().toString());
			if (cartSelected == null) {
				JOptionPane.showMessageDialog(this, "Choose one item in Cart first!");
			} else if (qty == 0) {
				int jawab = JOptionPane.showConfirmDialog(this, "Are You sure remove this item from cart?");
				if (jawab == JOptionPane.YES_OPTION) {
					Main.removeCart(activateUser, cartSelected.getItem());
				}
			} else {
				Main.updateCart(activateUser, cartSelected.getItem(), qty);
				tfItemName.setText("");
				tfItemPrice.setText("0 G");
				taItemDescription.setText("");
				sItemQuantity.setValue(0);
				tfTotalPrice.setText("0 G");
				cartSelected = null;
			}
			refreshTable();
		} else if (e.getSource() == buyBtn) {
			long total = 0;
			Vector<Cart> carts = Main.getvCartByUser(activateUser);
			for (Cart cart : carts) {
				total += cart.getQuantity() * cart.getItem().getPrice();
			}

			int jawab = JOptionPane.showConfirmDialog(this,
					"Total Price: " + total + "\nAre You sure want to buy all item?");
			if (jawab == JOptionPane.YES_OPTION) {
				Main.addHistory(activateUser, carts);
				for (Cart cart : carts) {
					Main.removeCart(activateUser, cart.getItem());
				}
			}
			refreshTable();
			try {
				HomePage.getInstance().showHistory.refreshTable();
			} catch (Exception e2) {
			}

		} else {
			for (JPanel jPanel : vCartPanel) {
				if (e.getSource() == jPanel) {
					cartSelected = allCartPanel.get(jPanel);
					Item itemSelected = cartSelected.getItem();
					tfItemName.setText(itemSelected.getName());
					tfItemPrice.setText(itemSelected.getPrice() + " G");
					taItemDescription.setText(itemSelected.getDescription());
					sItemQuantity.setValue(cartSelected.getQuantity());
					tfTotalPrice.setText(cartSelected.getQuantity() * itemSelected.getPrice() + " G");
				}
			}

		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
