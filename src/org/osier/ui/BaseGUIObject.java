package org.osier.ui;

import java.util.UUID;

public class BaseGUIObject {
	
	
	protected String name;
	protected String id;
	protected GUIChildren children;
	protected int x,y,width,height;
	
	protected BaseGUIObject() {
		name = "BaseGUIObject";
		id = UUID.randomUUID().toString();
		children = new GUIChildren();
	}
}
