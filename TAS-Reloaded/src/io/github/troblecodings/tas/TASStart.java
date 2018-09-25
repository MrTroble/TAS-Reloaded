package io.github.troblecodings.tas;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TASStart extends Application{

	public static JSONObject layout;
	
	public static void main(String[] args) throws Throwable {
		Path path = Paths.get("Layout.json");
		if(!Files.exists(path)) {
			Files.createFile(path);
		}
		String json = new String(Files.readAllBytes(path));
		layout = new JSONObject(json);
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		GridPane pane = new GridPane();
		Scene scene = new Scene(pane, 600, 400);
		stage.setScene(scene);
		stage.centerOnScreen();
		
		JSONArray array = layout.getJSONArray("layout");
		array.forEach(obj -> {
			JSONObject jobj = (JSONObject)obj;
			new Bank(pane, jobj);
		});
		
		stage.show();
	}

}
