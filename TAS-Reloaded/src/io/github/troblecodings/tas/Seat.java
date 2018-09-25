package io.github.troblecodings.tas;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Seat extends Label{

	public Seat(String name) {
		super(name);
		this.setFont(new Font("Arial", 20));
		this.setTextFill(Color.WHITE);
	}
	
}
