package org.osier.math;

public class MathService {
	
	public static Vector2 lerp2D(Vector2 begin, Vector2 end, float t) {
	    t = Math.max(0f, Math.min(1f, t)); // clamp 0–1

	    return new Vector2(
	        (int)(begin.x + (end.x - begin.x) * t),
	        (int)(begin.y + (end.y - begin.y) * t)
	    );
	}
}
