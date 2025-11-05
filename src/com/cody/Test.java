package com.cody;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import org.osier.OsierEngine;

public class Test extends OsierEngine{

	public Test(String title, int width, int height, boolean decorated) {
		super(title, width, height, decorated);
	}
	
	
	//TODO make sure window listeners are disconnect when window is set to invisible
	//TODO redo Scrollbar class and then handle input in the Window and BlockingDialog classes
	
	@Override
	public void load() {
		window.setIconImage("src/data/icons/osier.png");
		
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
	
	@Override
	public void mouseMoved(MouseEvent e, boolean processed) {
		
	}
}
