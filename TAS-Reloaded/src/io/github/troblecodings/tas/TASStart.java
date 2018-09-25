package io.github.troblecodings.tas;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TASStart extends Application {

	public static JSONObject layout;
	public static JSONObject school_class;
	public static JSONArray names;
	public static Random random;

	public static void main(String[] args) throws Throwable {
		Path path = Paths.get("Layout.json");
		if (!Files.exists(path)) {
			Files.createFile(path);
		}
		String json = new String(Files.readAllBytes(path));
		layout = new JSONObject(json);
		path = Paths.get("Layout.json");
		if (!Files.exists(path)) {
			Files.createFile(path);
		}
		json = new String(Files.readAllBytes(path));
		school_class = new JSONObject(json);
		random = new Random();
		names = school_class.getJSONArray("names");
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		GridPane pane = new GridPane();
		Scene scene = new Scene(pane, 600, 400);
		stage.setScene(scene);
		stage.centerOnScreen();

		StackPane center = new StackPane();
		center.setPrefSize(scene.getWidth(), 350);
		GridPane banks = new GridPane();
		banks.setVgap(15);
		banks.setHgap(15);
		JSONArray array = layout.getJSONArray("layout");
		array.forEach(obj -> {
			JSONObject jobj = (JSONObject) obj;
			new Bank(banks, jobj);
		});
		center.getChildren().add(banks);
		pane.add(center, 0, 1);
		StackPane.setAlignment(banks, Pos.CENTER);

		JSONArray banns = school_class.getJSONArray("banns");
		banks.getChildren().forEach(bank -> {
			Bank b = (Bank) bank;
			int i = 0;
			ObservableList<Node> nodes = b.getChildren();
			String last_name = null;
			for (Node n : nodes) {
				if (i == 0) {
					Label lab = (Label) n;
					int id = random.nextInt(names.length());
					String name = last_name = (String) names.get(id);
					names.remove(id);
					lab.setText(name);
				} else {
					Label lab = (Label) n;
					int id = random.nextInt(names.length());
					String name = last_name = (String) names.get(id);
					while (isBanned(last_name, name, banns) && names.length() > 0) {
						id = random.nextInt(names.length());
						name = last_name = (String) names.get(id);
					}
					if(names.length() <= 0) {
						
					}
					names.remove(id);
					lab.setText(name);
				}
				i++;
			}
		});

		stage.setResizable(false);
		stage.setIconified(false);
		stage.show();
	}
	
	public static boolean isBanned(String last, String name, JSONArray banns) {
		for(Object pair : banns) {
			JSONObject obj = (JSONObject) pair;
			String n1 = (String) obj.get("n1");
			String n2 = (String) obj.get("n2");
			if((last.equals(n1) || last.equals(n2)) && (name.equals(n1) || name.equals(n2))){
				return true;
			}
		}
		return false;
	}

}
