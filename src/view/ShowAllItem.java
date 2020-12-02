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
public class ShowAllItem extends JInternalFrame implements MouseListener {

	private User activateUser;
	private Item itemSelected;

	HashMap<JPanel, Item> allItemPanel = new HashMap<>();
	Vector<JPanel> vItemPanel = new Vector<>();

	JTextField tfItemName, tfItemPrice, tfTotalPrice;
	JLabel imgLbl;
	JTextArea taItemDescription;
	JSpinner sItemQuantity;
	JButton addToCartBtn;

	public ShowAllItem(User user) {
		activateUser = user;
		try {
			this.setFrameIcon(new ImageIcon("assets/icon25.png"));
		} catch (Exception e) {
		}

		setTitle("All Item");
		setVisible(true);

		setSize(1000, 600);
		setMinimumSize(new Dimension(1000, 600));
		setClosable(true);
		setIconifiable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		initializeAll();
	}

	private void initializeAll() {
		JScrollPane west = initalizeWestContent();
		JPanel center = initalizeCenterContent();

		this.add(west, BorderLayout.WEST);
		this.add(center, BorderLayout.CENTER);
	}

	private JPanel initalizeCenterContent() {
		JPanel pan0 = new JPanel();
		pan0.setLayout(new BoxLayout(pan0, BoxLayout.Y_AXIS));

		JPanel pan1 = new JPanel();
		imgLbl = new JLabel();
		imgLbl.setSize(new Dimension(100, 100));
		imgLbl.setPreferredSize(new Dimension(100, 100));
		imgLbl.setMinimumSize(new Dimension(100, 100));
		imgLbl.setMaximumSize(new Dimension(100, 100));
		pan1.add(imgLbl, BorderLayout.CENTER);
		pan0.add(pan1);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(8, 1));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel p0 = new JPanel();
		JLabel l0 = new JLabel("Item Name");
		l0.setPreferredSize(new Dimension(80, 40));
		p0.add(l0, BorderLayout.WEST);
		tfItemName = new JTextField();
		tfItemName.setEditable(false);
		tfItemName.setPreferredSize(new Dimension(350, 40));
		p0.add(tfItemName, BorderLayout.CENTER);
		panel.add(p0);

		JPanel p1 = new JPanel();
		JLabel l1 = new JLabel("Item Price");
		l1.setPreferredSize(new Dimension(80, 40));
		p1.add(l1, BorderLayout.WEST);
		tfItemPrice = new JTextField();
		tfItemPrice.setEditable(false);
		tfItemPrice.setPreferredSize(new Dimension(350, 40));
		p1.add(tfItemPrice, BorderLayout.CENTER);
		panel.add(p1);

		panel.add(new JLabel("Item Description"));
		taItemDescription = new JTextArea();
		taItemDescription.setEditable(false);
		taItemDescription.setLineWrap(true);
		taItemDescription.setWrapStyleWord(true);
		panel.add(taItemDescription);

		JPanel p2 = new JPanel();
		JLabel l2 = new JLabel("Quantity");
		l2.setPreferredSize(new Dimension(80, 40));
		p2.add(l2, BorderLayout.WEST);
		SpinnerModel sm = new SpinnerNumberModel(0, 0, 100, 1);
		sItemQuantity = new JSpinner(sm);
		sItemQuantity.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (itemSelected != null) {
					try {
						int qty = Integer.parseInt(sItemQuantity.getValue().toString());
						long price = itemSelected.getPrice();
						long total = qty * price;
						tfTotalPrice.setText(total + " G");
					} catch (NumberFormatException e1) {
					}
				}
			}
		});
		sItemQuantity.setPreferredSize(new Dimension(350, 40));
		p2.add(sItemQuantity, BorderLayout.CENTER);
		panel.add(p2);

		JPanel p3 = new JPanel();
		JLabel l3 = new JLabel("Total Price");
		l3.setPreferredSize(new Dimension(80, 40));
		p3.add(l3, BorderLayout.WEST);
		tfTotalPrice = new JTextField();
		tfTotalPrice.setEditable(false);
		tfTotalPrice.setPreferredSize(new Dimension(350, 40));
		p3.add(tfTotalPrice, BorderLayout.CENTER);
		panel.add(p3);

		panel.add(new JLabel());
		addToCartBtn = new JButton("Add to Cart");
		addToCartBtn.addMouseListener(this);
		panel.add(addToCartBtn);
		pan0.add(panel);

		return pan0;
	}

	private JScrollPane initalizeWestContent() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		Vector<Item> items = Main.getvItem();
		for (Item item : items) {
			JPanel content = initializeItemContent(item);
			content.addMouseListener(this);
			allItemPanel.put(content, item);
			vItemPanel.add(content);
			panel.add(content);
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(panel);
		scrollPane.setPreferredSize(new Dimension(530, 600));
		return scrollPane;
	}

	private JPanel initializeItemContent(Item item) {
		Border blackline = BorderFactory.createLineBorder(Color.black);
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setPreferredSize(new Dimension(500, 80));
		p1.setMaximumSize(new Dimension(500, 80));
		p1.setMinimumSize(new Dimension(500, 80));

		JPanel panel = new JPanel(new GridLayout(3, 1, 0, 0));
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

	public void refreshTable() {
		itemSelected = null;
		this.getContentPane().removeAll();
		initializeAll();
		this.revalidate();
		this.repaint();
	}

	private ImageIcon imageResizer(String path, int size) {
		ImageIcon imageIcon = new ImageIcon(path);
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == addToCartBtn) {
			int qty = Integer.parseInt(sItemQuantity.getValue().toString());
			if (itemSelected == null) {
				JOptionPane.showMessageDialog(this, "Choose item first!");
			} else if (qty > 0) {
				boolean newCart = Main.addCart(activateUser, itemSelected, qty);
				String text = "";
				if (newCart == true) {
					text = qty + " item(s) of " + itemSelected.getName() + " has been added to cart";
				} else {
					text = "You added " + qty + " more item(s) of " + itemSelected.getName() + " to cart";
				}
				JOptionPane.showMessageDialog(this, text);
				try {
					imgLbl = new JLabel();
					tfItemName.setText("");
					tfItemPrice.setText("0 G");
					taItemDescription.setText("");
					SpinnerModel sm = new SpinnerNumberModel(0, 0, 100, 1);
					sItemQuantity.setModel(sm);
					sItemQuantity.setValue(0);
					tfTotalPrice.setText("0 G");
					HomePage.getInstance().viewCarts.refreshTable();
				} catch (Exception e1) {
				}
				refreshTable();
			} else {
				JOptionPane.showMessageDialog(this, "Quantity can not be 0");
			}
		} else {
			for (JPanel jPanel : vItemPanel) {
				if (e.getSource() == jPanel) {
					itemSelected = allItemPanel.get(jPanel);
					imgLbl.setIcon(imageResizer("assets/prod/" + itemSelected.getImage(), 100));
					tfItemName.setText(itemSelected.getName());
					tfItemPrice.setText(itemSelected.getPrice() + " G");
					taItemDescription.setText(itemSelected.getDescription());
					Vector<Cart> vCart = Main.getvCartByUser(activateUser);
					int x = 100;
					for (Cart cart : vCart) {
						if (cart.getItem() == itemSelected) {
							x = 100 - cart.getQuantity();
						}
					}
					SpinnerModel sm = new SpinnerNumberModel(0, 0, x, 1);
					sItemQuantity.setModel(sm);
					sItemQuantity.setValue(0);
					tfTotalPrice.setText("0 G");
				}
			}

		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
