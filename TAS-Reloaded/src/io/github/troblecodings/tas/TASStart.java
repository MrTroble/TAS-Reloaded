package io.github.troblecodings.tas;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TASStart extends Application implements EventHandler<ActionEvent> {

	public static JSONObject layout;
	public static JSONObject school_class;
	public static JSONArray names;
	public static List<Object> names_cpy;
	public static Random random;

	public static void main(String[] args) throws Throwable {
		Path path = Paths.get("Layout.json");
		if (!Files.exists(path)) {
			Files.createFile(path);
		}
		String json = new String(Files.readAllBytes(path));
		layout = new JSONObject(json);
		path = Paths.get("Class.json");
		if (!Files.exists(path)) {
			Files.createFile(path);
		}
		json = new String(Files.readAllBytes(path));
		school_class = new JSONObject(json);
		random = new Random();
		names = school_class.getJSONArray("names");
		names_cpy = names.toList();
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		GridPane pane = new GridPane();
		Scene scene = new Scene(pane, 1200, 800);
		stage.setTitle("TAS Reloaded");
		stage.getIcons().add(new Image(TASStart.class.getResourceAsStream("LogoTM.png")));
		stage.setScene(scene);
		stage.centerOnScreen();
		pane.setPrefSize(scene.getWidth(), scene.getHeight());

		HBox box = new HBox();

		Button btn = new UButton("TAS on Github");
		btn.setId("git");
		btn.setOnAction(this);
		box.getChildren().add(btn);

		DatePicker picker = new DatePicker();
		picker.getEditor().setFont(new Font("Arial", 20));
		picker.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(5), new Insets(0))));
		box.getChildren().add(picker);

		box.setSpacing(scene.getWidth() - 465);

		pane.add(box, 0, 0);

		StackPane center = new StackPane();
		center.setPrefSize(scene.getWidth(), scene.getHeight() - box.getHeight());

		GridPane banks = new GridPane();
		banks.setVgap(35);
		banks.setHgap(55);
		JSONArray array = layout.getJSONArray("layout");
		array.forEach(obj -> {
			JSONObject jobj = (JSONObject) obj;
			new Bank(banks, jobj);
		});
		banks.setAlignment(Pos.CENTER);
		center.getChildren().add(banks);
		pane.add(center, 0, 1);

		UButton save = new UButton("Save");
		save.setId("save");
		save.setOnAction(this);
		save.addCostume(picker);
		save.addCostume(banks);
		pane.add(save, 0, 2);

		stage.setResizable(false);
		stage.setIconified(false);
		stage.show();

		JSONArray banns = school_class.getJSONArray("banns");
		retry: for (int z = 0; z <= 10; z++) {
			if (z == 10) {
				school_class.put("banns", new ArrayList<>());
				names_cpy = names.toList();
			}
			for (Object bank : banks.getChildren()) {
				Bank b = (Bank) bank;
				int i = 0;
				ObservableList<Node> nodes = b.getChildren();
				String last_name = null;
				for (Node n : nodes) {
					if (i == 0) {
						Label lab = (Label) n;
						int id = random.nextInt(names_cpy.size());
						String name = last_name = (String) names_cpy.get(id);
						names_cpy.remove(id);
						lab.setText(name);
					} else {
						Label lab = (Label) n;
						int id = random.nextInt(names_cpy.size());
						String name = (String) names_cpy.get(id);
						int x = 0;
						while (isBanned(last_name, name, banns)) {
							System.out.println(x);
							id = x;
							name = (String) names_cpy.get(id);
							x++;
							if (x >= names_cpy.size()) {
								names_cpy = names.toList();
								continue retry;
							}
						}
						names_cpy.remove(id);
						lab.setText(name);
					}
					i++;
				}
			}
			break retry;
		}
	}

	public static boolean isBanned(String last, String name, JSONArray banns) {
		for (Object pair : banns) {
			JSONObject obj = (JSONObject) pair;
			String n1 = (String) obj.get("n1");
			String n2 = (String) obj.get("n2");
			if ((last.equals(n1) || last.equals(n2)) && (name.equals(n1) || name.equals(n2))) {
				return true;
			}
		}
		return false;
	}

	public void saveAsPng(Node n, String dat) {
		SnapshotParameters par = new SnapshotParameters();
		WritableImage image = n.snapshot(par, null);

		File file = new File("Sitzplan-" + dat + ".png");

		try {
			file.createNewFile();
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handle(ActionEvent event) {
		UButton btn = (UButton) event.getSource();
		switch (btn.getId()) {
		case "git":
			try {
				Desktop.getDesktop().browse(new URI("https://github.com/Troblecodings/TAS-Reloaded"));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
			break;
		case "save":
			ArrayList<Object> obj = btn.getCostume();
			DatePicker picker = (DatePicker) obj.get(0);
			if (!picker.getEditor().getText().isEmpty()) {
				ArrayList<Object> ar = (ArrayList<Object>) school_class.getJSONArray("banns").toList();
				GridPane pane = (GridPane) obj.get(1);
				saveAsPng(pane, picker.getEditor().getText().replaceAll("/", "-"));
				for (Node b : pane.getChildren()) {
					Bank bank = (Bank) b;
					Node ln = null;
					for (Node n : bank.getChildren()) {
						if (ln == null) {
							ln = n;
							continue;
						}
						JSONObject jobj = new JSONObject();
						jobj.put("n1", ((Label) ln).getText());
						jobj.put("n2", ((Label) n).getText());
						ar.add(jobj);
						ln = n;
					}
				}
				school_class.put("banns", ar);
				school_class.put("names", names);
				try {
					FileWriter writer = new FileWriter("Class.json");
					school_class.write(writer);
					writer.close();
				} catch (JSONException | IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
