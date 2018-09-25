package io.github.troblecodings.tas;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Seat extends Label{

	public Seat(String name) {
		super(name);
		this.setFont(new Font("Arial", 35));
		this.setTextFill(Color.WHITE);
		this.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(5), new Insets(5))));
	}
	
}
