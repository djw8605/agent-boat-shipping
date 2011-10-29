package org.unl.ShippingSim;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;

public class HarborAgent implements Drawable, AbstractAgent {

	
	ArrayList<SellableItem> items; //items in the harbor
	protected Point pos; // position of the harbor
	protected int unload_capability; //unLoad capability of the harbor
	protected LinkedList<BoatAgent> boats_queue; //boats in the queue
	protected int completed_unload_weight; // the completed weight of the unload of the top boat (the boat may need more than one round to finish its unload)
	
	
	protected LinkedList<BoatAgent> boat_queue;
	protected int boat_unload_counter = 0;

	
	public HarborAgent(int X, int Y, OceanSpace space, ArrayList<SellableItem> Items) {
		pos = new Point();
		pos.x = X;
		pos.y = Y;
		items = Items;
		completed_unload_weight = 0;
		boats_queue = new LinkedList<BoatAgent>();
		
		//boat_queue = new LinkedList<BoatAgent>();
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
	
	// do a round unload in the harbor, and return the boats go out of the queue in this round
	public LinkedList<BoatAgent> Unload(){
		LinkedList<BoatAgent> out_boats = new LinkedList<BoatAgent>();
		int unload_weight = unload_capability + completed_unload_weight;
		while (unload_weight > boats_queue.peek().getUnloadTime())
		{
			BoatAgent boat = boats_queue.getFirst();
			out_boats.add(boat);
			unload_weight -= boat.getUnloadTime();
		}
		completed_unload_weight = unload_weight;
		return out_boats;
	}
	
	public void draw(SimGraphics graphic) {
		// Draw the harbor
		graphic.drawFastRoundRect(Color.red);
		
		// Draw the current prices
			
	}
	
	public void enqueueBoat(BoatAgent boat) {
		// Enqueue the boat to be unloaded
		boat_queue.add(boat);
		
		if (boat_queue.size() == 1) {
			this.boat_unload_counter = boat.getUnloadTime();
		}
		
	}

	public double getQueueSize() {
		return (double)this.boat_queue.size();
	}

	@Override
	public void step() {
		
		// Check if the current boat should sent on it's way
		if ((boat_unload_counter == 0) && (boat_queue.size() > 0)) {
			BoatAgent current_boat = boat_queue.pop();
			current_boat.doneLoading();
			
			if (boat_queue.size() > 0)
				boat_unload_counter = boat_queue.get(0).getUnloadTime();
			
		} else if (boat_queue.size() > 0) {
			boat_unload_counter -= 1;
		}	
	}
}
