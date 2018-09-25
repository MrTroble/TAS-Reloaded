package io.github.troblecodings.tas;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UButton extends Button {

	private ArrayList<Object> costume = new ArrayList<>();
	
	public UButton(String str) {
		super(str);
		this.setFont(new Font("Arial", 25));
		this.setTextFill(Color.WHITE);
		this.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(5), new Insets(0))));
	}
	
	public void addCostume(Object obj) {
		costume.add(obj);
	}
	
	public ArrayList<Object> getCostume() {
		return costume;
	}
}
