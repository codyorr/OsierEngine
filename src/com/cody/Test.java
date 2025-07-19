package com.cody;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import org.osier.OsierEngine;
import org.osier.ui.TextButton;

public class Test extends OsierEngine{

	public Test(String title, int width, int height, boolean decorated) {
		super(title, width, height, decorated);
	}
	
	
	//TODO make sure window listeners are disconnect when window is set to invisible
	//TODO redo Scrollbar class and then handle input in the Window and BlockingDialog classes
	
	private TextButton frame;
	@Override
	public void load() {
		window.setIconImage("src/data/icons/osier.png");
		frame = new TextButton() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("CLICK");
				frame.setText("CLICKED");
				frame.setParent(null);
			}
			public void mousePressed(MouseEvent e) {
				System.out.println("PRESS");
				frame.setText("PRESSED");
			}
			public void mouseReleased(MouseEvent e) {
				System.out.println("RELEASED");
				frame.setText("RELEASED");
			}
			public void mouseEntered(MouseEvent e) {
				System.out.println("ENTERED");
				frame.setText("ENTERED");
			}
			public void mouseExited(MouseEvent e) {
				System.out.println("EXITED");
				frame.setText("EXITED");
			}
		};
		frame.setSize(0, 0.5f, 0, 0.5f);
		frame.setPosition(0, 0.5f, 0, 0.5f);
		frame.setOrigin(0.5f, 0.5f);
		frame.setCornerRadius(8);
		frame.setTextScaled(true);
		frame.setParent(window);
	}

	@Override
	public void update() {

	}
	
	@Override
	public void render(Graphics2D g) {
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e, boolean processed) {
		
	}
}
