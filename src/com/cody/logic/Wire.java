package com.cody.logic;

import java.awt.Graphics2D;
import java.util.Arrays;

public class Wire {
	
	private int[] segments;
	
	public Wire() {}
	
	public void addSegment(int sx, int sy, int ex, int ey) {
	    if (segments != null) {
	        int[] newSegments = Arrays.copyOf(segments, segments.length + 4);
	        int i = segments.length;
	        newSegments[i] = sx;
	        newSegments[i + 1] = sy;
	        newSegments[i + 2] = ex;
	        newSegments[i + 3] = ey;
	        segments = newSegments;
	    }else {
			segments = new int[4];
			segments[0] = sx;
			segments[1] = sy;
			segments[2] = ex;
			segments[3] = ey;
		}
	}
	
	public void render(Graphics2D g) {
		for(int i = 0; i < segments.length; i+=4) {
			g.drawLine(segments[i],segments[i+1], segments[i+2], segments[i+3]);
		}
	}
}
