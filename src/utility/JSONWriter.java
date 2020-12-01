package utility;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.Item;
import program.Main;

public class JSONWriter {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONWriter() {
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();

		Vector<Item> vItem = Main.getvItem();
		System.out.println(vItem.size());
		for (Item item : vItem) {
			Map m = new LinkedHashMap(4);
			
			m.put("name", item.getName());
			m.put("price", item.getPrice());
			m.put("description", item.getDescription());
			m.put("image", item.getImage());
			ja.add(m);
		}
		jo.put("items", ja); 


		PrintWriter pw;
		try {
			pw = new PrintWriter("assets/items.json");
			pw.write(jo.toJSONString());
			
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
