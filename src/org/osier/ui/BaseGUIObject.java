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
	
	protected void add(GUIObject obj) {
		children.add(obj);
		obj.parent = this;
	}
	
	protected void remove(GUIObject obj) {
		children.remove(obj);
		obj.parent = null;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public <T extends GUIObject> T findFirstChild(String name) {
		return children.find(name);
	}
	
	public <T extends GUIObject> T getChild(int i) {
		return children.get(i);
	}
	
	public GUIChildren getChildren() {
		return children;
	}
	
}
