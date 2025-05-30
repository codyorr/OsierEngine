package com.cody;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.File;

import org.osier.OsierEngine;
import org.osier.ui.GUIFrame;
import org.osier.ui.Window;

public class Test extends OsierEngine{

	public Test(String title, int width, int height, boolean decorated) {
		super(title, width, height, decorated);
	}
	
	private GUIFrame frame, frame2;
	@Override
	public void load() {
		window.setIconImage("src/data/icons/osier.png");
		frame = new GUIFrame();
		frame.setSize(0, 0.5f, 0, 0.5f);
		frame.setPosition(0, 0.5f, 0, 0.5f);
		frame.setOrigin(0.5f, 0.5f);
		frame.setCornerRadius(8);
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
		System.out.println("CLICK");
	}
}
