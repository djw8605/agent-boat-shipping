package org.unl.ShippingSim;

import java.awt.Color;
import java.awt.Dimension;

import uchicago.src.sim.engine.SimpleModel;
import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;
import uchicago.src.sim.util.Random;

public class BoatAgent extends SimpleModel implements Drawable, AbstractAgent {

	// The position of the boat
	protected float xpos;
	protected float ypos;
	protected int max_x;
	protected int max_y;
	
	//  Speed of the boat
	protected double speed;
	
	// The global space
	protected OceanSpace space;
	
	// Whether we are at a harbor or not
	protected boolean loading = false;
	
	// The harbor we are traveling to
	protected HarborAgent target_harbor;
	
	// The size of the boat, affects unload time
	protected double size;
	
	protected double risk_factor = 1.0;
	
	protected double queue_effect = 1.0;
	
	// The item the boat is currently holding
	protected int item_index = 0;
	
	// Current money of the boat
	protected double money = 0.0;
	
	
	
	public BoatAgent(int x, int y, OceanSpace space) {
		this.xpos = x;
		this.ypos = y;
		this.space = space;
		Dimension space_dimension = space.getSize();
		max_y = space_dimension.height - 1;
		max_x = space_dimension.width - 1;
		speed = (float)0.2;
		

	}
	
	
	public void step() {
		
		// If we're loading, don't do anything
		if (loading == true) 
			return;
		
		// First iteration, the target_harbor will be null
		if (target_harbor == null) {
			target_harbor = CalculateNextHarbor(null);
		}
		
		int harborx = target_harbor.getX();
		int harbory = target_harbor.getY();
		
		double a = (double) harborx - xpos;
		double b = (double) harbory - ypos;
		double distance = Math.sqrt((Math.pow(a, 2) +  Math.pow(b, 2)));
		
		// This is basically doing an inverse sin/cos to find the new position.
		double new_xpos = this.speed * (a/(distance+1)) + xpos;
		double new_ypos = this.speed * (b/(distance+1)) + ypos;


		space.putObjectAt((int)xpos, (int)ypos, null);
		
		// Be sure to not place the boats off the map
	    xpos = Math.max(Math.min((float)new_xpos, max_x), 0);
	    ypos = Math.max(Math.min((float)new_ypos, max_y), 0);
	    
	    space.putObjectAt((int)xpos, (int)ypos, this);

	    
	    // Check if we've arrived at the harbor.
	    if ((harborx == Math.round(xpos)) && (harbory == Math.round(ypos))) {
	    	this.target_harbor.AddBoat(this);
	    	this.loading = true;
	    	
	    	// Sell the items we are carrying
	    	if (this.item_index >= 0) {
	    		money += this.target_harbor.getItems().get(this.item_index).HarborBuyItem(this.size);
	    	}
	    	
	    }
		
	}
	
	public void draw(SimGraphics g) {
		
		// Only draw the boat if it's not in harbor
		if (this.loading == false)
			g.drawFastRoundRect(Color.green);
	}
	
	/**
	 * The Harbor will call this when the boat is done loading, and should
	 * calculate the next harbor to go to.
	 */
	public void doneLoading() {
		// First, set us as done loading
		loading = false;
		
		// Calculate the next harbor to go to
		this.target_harbor = CalculateNextHarbor(this.target_harbor);
	}
	

	/**
	 * Method to calculate the next harbor to travel to.
	 */
	protected HarborAgent CalculateNextHarbor(HarborAgent current_harbor) {
		
		
		double max_profit = Double.MIN_VALUE;
		HarborAgent max_harbor = null;
		int max_item = 0;
		
		// For each potential destination harbor
		for (int i = 0; i < space.GetHarbors().size(); i++) {
			HarborAgent dest_harbor = space.GetHarbors().get(i);
			
			// For each item
			for(int a = 0; a < space.GetHarbors().get(i).getItems().size(); a++) {
				if (dest_harbor == current_harbor) 
					continue;
				
				double profit = this.CalculateExpectedProfit(a, current_harbor, dest_harbor);
				SellableItem item = current_harbor.getItems().get(a);
				if ( (profit > max_profit) && (item.GetInventory() > this.size)) {
					max_profit = profit;
					max_harbor = dest_harbor;
					max_item = a;
				}
			}
		}
		
		if (max_harbor == null) {
			this.item_index = -1;
			return this.target_harbor;
		} else {
			PurchaseItems(max_item, max_harbor);
			this.item_index = max_item;
			return max_harbor;
		}
		
	}
	
	/**
	 * Method to calculate the expected profit given item & harbor
	 * Boat will travel from buy_harbor -> sell_harbor
	 * @return Expected profit
	 */
	protected double CalculateExpectedProfit(int item_index, HarborAgent buy_harbor, HarborAgent sell_harbor) {
		
		double profit = 0.0;
		
		double sell_price = sell_harbor.getItems().get(item_index).GetBoat2HarborPrice() * this.size;
		double buy_price = buy_harbor.getItems().get(item_index).GetHarbor2BoatPrice() * this.size;
		double uncertainty = this.risk_factor * this.Uncertainty(buy_harbor, sell_harbor);
		double fuelprices =  this.space.GetFuelPrices() * this.size * BoatAgent.HarborDistance(buy_harbor, sell_harbor);
		
		profit = sell_price - buy_price - uncertainty - fuelprices;
		
		return profit;
	}
	
	
	/**
	 * Calculate the uncertainty factor
	 * @param buy_harbor HarborAgent - Harbor that the item will be bought from
	 * @param sell_harbor HarborAgent - Harbor the item will be sold
	 * @return
	 */
	protected double Uncertainty(HarborAgent buy_harbor, HarborAgent sell_harbor) {
		double uncertainty = 0;
		
		double distance = BoatAgent.HarborDistance(buy_harbor, sell_harbor);
		uncertainty = distance * this.speed + this.queue_effect * sell_harbor.getBoatNum();
		
		return uncertainty;
	}

	/**
	 * Function to get the distance between 2 harbors
	 * @param h1 HarborAgent
	 * @param h2 HarborAgent
	 * @return distance
	 */
	protected static double HarborDistance(HarborAgent h1, HarborAgent h2) {
		return Math.sqrt(Math.pow((h1.getX() - h2.getX()), 2) + Math.pow((h1.getY() - h2.getY()), 2));
	}
	

	/**
	 * Purchase these items
	 * @param item_index
	 * @param from_harbor
	 * @return if purchase was successful
	 */
	protected boolean PurchaseItems(int item_index, HarborAgent from_harbor) {
		SellableItem buy_item = from_harbor.getItems().get(item_index);
		boolean purchase_success = buy_item.BoatBuyItem(this.size);
		
		return purchase_success;
	}
	
	
	/**
	 * Method to get the unload time of this boat
	 */
	public int getUnloadTime() {
		return (int)this.size/10;
	}
	
	/**
	 * Set the current harbor
	 * @param harbor
	 */
	public void SetHarbor(HarborAgent harbor) {
		harbor.AddBoat(this);
		this.target_harbor = harbor;
		this.loading = true;
	}
	
	public void setSize(double size) {
		this.size = size;
	}
	
	/**
	 * Set the queue effect
	 */
	public void setQueueEffect(float queue_effect) {
		this.queue_effect = queue_effect;
	}
	
	/**
	 * Set uncertainty effect
	 */
	public void setUncertainty(double uncertainty) {
		this.risk_factor = uncertainty;
	}
	
	@Override
	public int getX() {
		return (int)this.xpos;
	}


	@Override
	public int getY() {
		return (int)this.ypos;
	}

}
