package utility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import model.Item;
import program.Main;

public class JSONReader {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONReader() {
		try {
			Object obj = new JSONParser().parse(new FileReader("assets/items.json"));
			JSONObject jo = (JSONObject) obj;
			JSONArray ja = (JSONArray) jo.get("items");

			Iterator<Map.Entry> itr1;
			Iterator itr2 = ja.iterator();

			while (itr2.hasNext()) {
				itr1 = ((Map) itr2.next()).entrySet().iterator();
				String name="", description ="", image = "";
				int price = 0;
				while (itr1.hasNext()) {
					Map.Entry pair = itr1.next();
					switch (pair.getKey().toString()) {
					case "name":
						name = pair.getValue().toString();
						break;
					case "description":
						description = pair.getValue().toString();
						break;
					case "price":
						price = Integer.parseInt(pair.getValue().toString());
						break;
					case "image":
						image = pair.getValue().toString();
						break;
					}
				}
				Main.addItem(new Item(name, price, description, image));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
