package org.osier.ui;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GUIChildren {
	
	protected List<GUIObject> list;
	
	public GUIChildren() {
		list = new ArrayList<GUIObject>();
	}
	
	
	@SuppressWarnings("unchecked")
	public <T extends GUIObject> T get(int i) {
		return (T) list.get(i);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GUIObject> T find(String name) {
		for(GUIObject child : list) {
			if(child.name.equals(name)) {
				return (T) child;
			}
		}
		return null;
	}
	
	
	protected void add(GUIObject obj) {
		list.add(obj);
		sortByDisplayOrder();
	}
	
	protected void remove(GUIObject obj) {
		list.remove(obj);
		sortByDisplayOrder();
	}
	
	private void sortByDisplayOrder() {
		list.sort(Comparator.comparingInt(GUIObject::getDisplayOrder));
	}
	
	protected void updateSizes() {
		for(GUIObject child : list) {
			child.updateSize();
		}
	}
	
	protected void updatePositions() {
		for(GUIObject child : list) {
			child.updatePosition();
		}
	}
	
	protected void render(Graphics2D g) {
		for(GUIObject child : list) {
			child.render(g);
		}
	}
	
	
}
