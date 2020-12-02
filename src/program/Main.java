package program;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Vector;

import model.Cart;
import model.Item;
import model.User;
import utility.JSONReader;
import utility.JSONWriter;
import view.*;

public class Main {
	public static final String FONT_NAME = "Futura Md BT";
	public static final int FONT_STYLE = Font.ROMAN_BASELINE;
	public static final int FONT_SIZE_CONTENT = 16;
	public static final int FONT_SIZE_TITLE = 20;
	public static final Color THEME_COLOR = Color.decode("#c7fbaa");

	private static HashMap<User, Vector<Cart>> history;

	private static Vector<User> vUser;
	private static Vector<Item> vItem;
	private static Vector<Cart> vCart;

	private static void initializeCart() {
		vCart = new Vector<>();
	}

	public static Vector<Cart> getAllvCart() {
		return vCart;
	}

	public static Vector<Cart> getvCartByUser(User user) {
		Vector<Cart> tCart = new Vector<>();
		for (Cart cart : vCart) {
			if (cart.getUser() == user) {
				tCart.add(cart);
			}
		}
		return tCart;
	}

	public static boolean addCart(User user, Item item, int quantity) {
		for (int i = 0; i < vCart.size(); i++) {
			Cart c = vCart.get(i);
			if (c.getUser() == user && c.getItem() == item) {
				c.addQuantity(quantity);
				return false;
			}
		}

		vCart.add(new Cart(user, item, quantity));
		return true;
	}

	public static void updateCart(User user, Item item, int quantity) {
		for (int i = 0; i < vCart.size(); i++) {
			Cart c = vCart.get(i);
			if (c.getUser() == user && c.getItem() == item) {
				c.setQuantity(quantity);
				return;
			}
		}
	}

	public static void removeCart(User user, Item item) {
		for (int i = 0; i < vCart.size(); i++) {
			Cart c = vCart.get(i);
			if (c.getUser() == user && c.getItem() == item) {
				vCart.remove(c);
				return;
			}
		}
	}

	private static void initializeUser() {
		vUser = new Vector<>();
//		vUser.add(new User("Jethro Otto", "LAZERWAVE", "jethro@otto.com", "asd123"));
	}

	public static Vector<User> getvUser() {
		return vUser;
	}

	public static void addUser(User user) {
		vUser.add(user);
	}

	private static void initializeHistory() {
		history = new HashMap<>();
	}

	public static void addHistory(User user, Vector<Cart> carts) {
		if (history.get(user) == null) {
			history.put(user, carts);
		} else {
			Vector<Cart> t = history.get(user);
			for (Cart cart : carts) {
				t.add(cart);
			}

			history.put(user, t);
		}
	}

	public static Vector<Cart> getHistory(User user) {
		return history.get(user);
	}

	private static void initializeItem() {
		vItem = new Vector<>();
	}

	public static void addItem(Item item) {
		vItem.add(item);
	}

	public static Vector<Item> getvItem() {
		return vItem;
	}

	public static void setItem(Item item, int index) {
		vItem.set(index, item);
	}

	static void jsonMaker() {
		vItem.add(new Item("Ring Of health", 825, "+6.5 HP Regen", "ring_of_health.png"));
		vItem.add(new Item("Ring Of Tarrasque", 650, "+4 HP Regen", "ring_of_tarrasque.png"));
		vItem.add(new Item("Blades Of Attack", 450, "+9 Damage", "blades_of_attack.png"));
		vItem.add(new Item("Chainmail", 550, "+4 Armor", "chainmail.png"));
		vItem.add(new Item("Robe Of The Magi", 450, "+6 Intelligence", "robe_of_the_magi.png"));
		vItem.add(new Item("Staff Of Wizardry", 1000, "+10 Intelligence", "staff_of_wizardry.png"));
		vItem.add(new Item("Blades Of Alacrity", 1000, "+10 Agility", "blade_of_alacrity.png"));
		vItem.add(new Item("Crown", 450, "+4 All Attributes", "crown.png"));
		vItem.add(new Item("Teyvat Fried Egg", 200, "Revives and restores 50/100/150 HP to the target fallen character",
				"Teyvat_Fried_Egg.png"));
		vItem.add(new Item("Fisherman's Toast", 1025, "Increases the party's defense by 88/107/126 for 300 seconds",
				"Fisherman_Toast.png"));
		vItem.add(new Item("Chicken-Mushroom Skewer", 450,
				"Restores 8/9/10% of Max HP and an additional 800/1,000/1,200 HP to the target character",
				"Chicken-Mushroom_Skewer.png"));
		vItem.add(new Item("Apple Cider Vinegar", 1500,
				"Restores 26% of Max HP and regenerates 570 HP every 5 seconds for 30 seconds for the selected character",
				"Apple_Cider_Vinegar.png"));
		vItem.add(new Item("Berry & Mint Burst", 1500, "Increases the party's Critical Rate by 16% for 300 seconds",
				"Berry_Mint_Burst.png"));
		vItem.add(new Item("Wolfhook Juice", 1500, "Increases the party's attack by 114 for 300 seconds",
				"Wolfhook_Juice.png"));
		vItem.add(new Item("Pop's Teas", 1000,
				"Restores 14% of Max HP and regenerates 350 HP every 5 seconds for 30 seconds for the selected character",
				"Pop_Teas.png"));
		new JSONWriter();
	}

	static void jsonReader() {
		// System.out.println(vItem.size());
		new JSONReader();

		// System.out.println(vItem.size());
		// for (Item i : vItem) {
		// System.out.println(i.getName() + " # " + i.getPrice() + " # " +
		// i.getDescription());
		// }

	}

	public static void main(String[] args) {
		initializeUser();
		initializeItem();
		initializeCart();
		initializeHistory();
		// jsonMaker();
		jsonReader();

		new LoginPage();
		// HomePage.getInstance().setUser(vUser.get(0));

	}

}
