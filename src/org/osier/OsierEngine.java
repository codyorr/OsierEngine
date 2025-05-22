package org.osier;

import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.image.BufferStrategy;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.osier.listeners.CoreListener;
import org.osier.ui.Window;

public class OsierEngine implements CoreListener{
	
	private Graphics2D g;
	public Window window;
	private BufferStrategy bs;
	private Thread renderThread;
	private GraphicsDevice display;
	private ConcurrentLinkedQueue inputQueue;
	
	public OsierEngine(String title, int width, int height, boolean decorated) {
		
	}
	
	public void start() {
		
	}
	
	public void stop() {
		
	}
	
	
	
	
}
