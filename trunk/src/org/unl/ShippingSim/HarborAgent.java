package org.unl.ShippingSim;

import java.awt.Point;
import java.util.ArrayList;

import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;

public class HarborAgent implements Drawable {

	
	ArrayList<SellableItem> items;
	protected Point pos;

	
	public HarborAgent(int X, int Y, OceanSpace space, ArrayList<SellableItem> Items) {
		pos.x = X;
		pos.y = Y;
		items = Items;
	}
	
	
	public ArrayList<SellableItem> getItems() {
		return items;
	}
	
	public Point GetPos(){
		return pos;
	}
	public int getX() {
		return pos.x;
	}
	
	public int getY() {
		return pos.y;
	}
	
	public void draw(SimGraphics graphic) {
		
		
	}
	
}
