package com.cody;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import org.osier.math.Vector2;
import org.osier.ui.Window;

public class ParticleBackground {
	
    static Vector2[] particles;
    static Vector2[] velocity;
    static float[] speedFactors;      // per-particle speed multiplier
    
    static Vector2 mousePos = new Vector2(0, 0); // store mouse position
    static float attractRadius = 75f; // radius for drawing lines
	static Window window;
	
    public static void load(Window mainWindow) {
    	window = mainWindow;
        int count = 100;
        particles = new Vector2[count];
        velocity  = new Vector2[count];
        speedFactors = new float[count];

        for (int i = 0; i < count; i++) {
            particles[i] = new Vector2(
                (float)(Math.random() * window.getWidth()),
                (float)(Math.random() * window.getHeight())
            );

            velocity[i] = new Vector2(
                (float)(Math.random() * 100 - 50),
                (float)(Math.random() * 100 - 50)
            );

            speedFactors[i] = 0.3f + (float)Math.random() * 0.5f; // random speed 0.3-0.8
        }
    }

    
    public static void update(float deltaTime) {
        int w = window.getWidth();
        int h = window.getHeight();

        for (int i = 0; i < particles.length; i++) {
            particles[i].x += velocity[i].x * deltaTime * speedFactors[i];
            particles[i].y += velocity[i].y * deltaTime * speedFactors[i];

            if (particles[i].x < 0 || particles[i].x > w ||
                particles[i].y < 0 || particles[i].y > h) {
                respawnAtEdge(i);
            }
        }
    }

    
    public static void render(Graphics2D g) {    	
        float particleRadius = 3;
        float particleCenterOffset = particleRadius / 2f; 
        float connectionRadius = 65f;           
        float connectionRadiusSqr = connectionRadius * connectionRadius;

        // draw lines between particles (distance-based transparency)
        for (int i = 0; i < particles.length; i++) {
            Vector2 p1 = particles[i];
            float p1CenterX = p1.x + particleCenterOffset;
            float p1CenterY = p1.y + particleCenterOffset;

            for (int j = i + 1; j < particles.length; j++) {
                Vector2 p2 = particles[j];
                float p2CenterX = p2.x + particleCenterOffset;
                float p2CenterY = p2.y + particleCenterOffset;

                float dx = p1CenterX - p2CenterX;
                float dy = p1CenterY - p2CenterY;
                float distSqr = dx*dx + dy*dy;

                if (distSqr <= connectionRadiusSqr) {
                    float alpha = 1f - (float)Math.sqrt(distSqr) / connectionRadius;
                    alpha = Math.max(0f, Math.min(1f, alpha)); 
                    g.setColor(new Color(0.02f, 0.02f, 0.02f, alpha));
                    g.drawLine((int)p1CenterX, (int)p1CenterY, (int)p2CenterX, (int)p2CenterY);
                }
            }
        }

        // draw particles and glow/laser with background light effect
        for (Vector2 p : particles) {
            float pCenterX = p.x + particleCenterOffset;
            float pCenterY = p.y + particleCenterOffset;

            // particle body
            g.setColor(Color.black);
            g.fillOval((int)p.x, (int)p.y, (int)particleRadius, (int)particleRadius);

            float dx = pCenterX - mousePos.x;
            float dy = pCenterY - mousePos.y;
            float dist = (float)Math.sqrt(dx*dx + dy*dy);

            if (dist <= attractRadius) {
                float alpha = 1f - dist / attractRadius;
                alpha = Math.max(0f, Math.min(1f, alpha));

                // --- FAKE LIGHT REFRACTION ALONG LASER ---
                int steps = 15; // how many faint circles along the laser
                for (int s = 1; s <= steps; s++) {
                    float t = s / (float)steps;
                    float lx = pCenterX + dx * t;
                    float ly = pCenterY + dy * t;
                    float lightRadius = 8 * (1f - t); // taper light
                    float lightAlpha = alpha * (1f - t) * 0.1f; // very subtle
                    g.setColor(new Color(1f, 0f, 0f, lightAlpha));
                    g.fillOval((int)(lx - lightRadius), (int)(ly - lightRadius), (int)(lightRadius*2), (int)(lightRadius*2));
                }

                // --- GLOW EFFECT ---
                int glowRadius = 12;
                for (int r = glowRadius; r >= 1; r--) {
                    float layerAlpha = alpha * (1f - (float)r / glowRadius) * 0.5f;
                    g.setColor(new Color(1f, 0f, 0f, layerAlpha));
                    g.fillOval((int)(pCenterX - r), (int)(pCenterY - r), r*2, r*2);
                }

                // --- LASER EFFECT ---
                g.setColor(new Color(1f, 0f, 0f, alpha));
                g.drawLine((int)pCenterX, (int)pCenterY, (int)mousePos.x, (int)mousePos.y);
            }
        }
    }
    
    private static void respawnAtEdge(int i) {
        double r = Math.random();

        if (r < 0.25) { // top
            particles[i].x = (float)(Math.random() * window.getWidth());
            particles[i].y = 0;
        } else if (r < 0.5) { // bottom
            particles[i].x = (float)(Math.random() * window.getWidth());
            particles[i].y = window.getHeight();
        } else if (r < 0.75) { // left
            particles[i].x = 0;
            particles[i].y = (float)(Math.random() * window.getHeight());
        } else { // right
            particles[i].x = window.getWidth();
            particles[i].y = (float)(Math.random() * window.getHeight());
        }

        // give it a new velocity
        velocity[i].x = (float)(Math.random() * 100 - 50);
        velocity[i].y = (float)(Math.random() * 100 - 50);

        // assign a new random speed factor for this particle
        speedFactors[i] = 0.3f + (float)Math.random() * 0.5f;
    }

    
    public static void mouseMoved(MouseEvent e, boolean isDragging) {
        mousePos.x = e.getX();
        mousePos.y = e.getY();
    }

}
