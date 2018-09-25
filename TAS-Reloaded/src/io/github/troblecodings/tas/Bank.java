package io.github.troblecodings.tas;

import org.json.JSONObject;

import javafx.scene.layout.GridPane;

public class Bank extends GridPane{

	public Bank(GridPane root, JSONObject obj) {
		root.add(this, obj.getInt("bankx"), obj.getInt("banky"));
		for(int i = 0;i < obj.getInt("size");i++) {
			this.add(new Seat(), 0, i);
		}
	}
	
}
