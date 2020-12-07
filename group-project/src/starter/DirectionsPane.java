//This file creates the information in the directions page
package starter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import acm.graphics.*;

public class DirectionsPane extends GraphicsPane {
	private MainApplication program;
	
	private GButton backButton;
	private GLabel backLabel;

	public DirectionsPane(MainApplication app) {
		super();
		program = app;
		
		//Back button and label
		backButton = new GButton("", 810, 800, 180, 75);
		backButton.setFillColor(Color.ORANGE);
		backLabel = new GLabel("BACK", 830, 855);
		backLabel.setFont("TimesRoman-Bold-48");
		backLabel.setColor(Color.WHITE);
	}
	
	@Override
	public void showContents() {
		program.add(backButton);
		program.add(backLabel);
	}

	@Override
	public void hideContents() {
		program.remove(backButton);
		program.remove(backLabel);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == backButton) {
			program.switchToMenu();	
		}
		if (obj == backLabel) {
			program.switchToMenu();	
		}
	}

}
