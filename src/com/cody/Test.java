package com.cody;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import org.osier.OsierEngine;
import org.osier.ui.GUIFrame;
import org.osier.ui.ImageLabel;

public class Test extends OsierEngine{

	public Test(String title, int width, int height, boolean decorated) {
		super(title, width, height, decorated);
	}
	
	private GUIFrame frame;
	private ImageLabel label;
	private int angle;
	@Override
	public void load() {
		frame = new GUIFrame();
		frame.setSize(0, 0.5f, 0, 0.5f);
		frame.setPosition(0, 0.5f, 0, 0.5f);
		frame.setOrigin(0.5f, 0.5f);
		frame.setParent(window);
		
		label = new ImageLabel();
		label.setBackgroundColor(Color.green);
		label.setBorderSize(0);
		label.setParent(frame);
		
		
	}

	@Override
	public void update() {
		frame.setRotationAngle(Math.toRadians(angle));
		angle+=1;
		if(angle>=360) {
			angle = 0;
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e, boolean processed) {
		
	}
}
