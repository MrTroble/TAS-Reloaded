package io.github.troblecodings.tas;

import org.json.JSONObject;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Bank extends GridPane{

	public Bank(GridPane root, JSONObject obj) {
		root.add(this, obj.getInt("bankx"), obj.getInt("banky"));
		this.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(5), new Insets(0))));
		for(int i = 0;i < obj.getInt("size");i++) {
			Seat seat = new Seat("None");
			this.add(seat, i, 0);
			GridPane.setMargin(seat, new Insets(10));
		}
	}
	
}
