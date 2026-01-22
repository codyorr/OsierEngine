package com.cody;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;

import org.osier.OsierEngine;
import org.osier.math.Vector2;

public class Test extends OsierEngine {

    Image image;
    
    public Test(String title, int width, int height, boolean decorated) {
        super(title, width, height, decorated);
    }

    @Override
    public void load() {
    	window.setIconImage("src/data/icons/osier.png");
    	image = this.loadImage("src/data/sprites/background.png");
    	ParticleBackground.load(window);
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