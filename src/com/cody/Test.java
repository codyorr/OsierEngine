package com.cody;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import org.osier.OsierEngine;
import org.osier.ui.TextButton;

public class Test extends OsierEngine {

    Image image;
    TextButton button;
    public Test(String title, int width, int height, boolean decorated) {
        super(title, width, height, decorated);
    }

    @Override
    public void load() {
    	window.setIconImage("src/data/icons/osier.png");
    	image = this.loadImage("src/data/sprites/background.png");
    	ParticleBackground.load(window);
    	
    	button = new TextButton();
    	button.setText("Start");
    	button.setOrigin(0.5f, 0.5f);
    	button.setSize(150,0,25,0);
    	button.setPosition(0,0.5f,0,0.25f);
    	button.setBackgroundColor(new Color(0.4f,0.4f,0.4f,0.35f));
    	button.setParent(window);
    }

    @Override
    public void update(float deltaTime) {
        ParticleBackground.update(deltaTime);
    }

    @Override
    public void render(Graphics2D g) {
    	g.drawImage(image, 0,0, 300,400,null);
    	ParticleBackground.render(g);
       
    }

    @Override
    public void mouseMoved(MouseEvent e, boolean isDragging) {
       ParticleBackground.mouseMoved(e, isDragging);
    }
}