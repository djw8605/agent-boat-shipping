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
	
	protected double risk_factory = 1.0;
	
	protected double queue_effect = 1.0;
	
	
	
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
		double new_xpos = this.speed * (a/distance) + xpos;
		double new_ypos = this.speed * (b/distance) + ypos;


		space.putObjectAt((int)xpos, (int)ypos, null);
		
		// Be sure to not place the boats off the map
	    xpos = Math.max(Math.min((float)new_xpos, max_x), 0);
	    ypos = Math.max(Math.min((float)new_ypos, max_y), 0);
	    
	    space.putObjectAt((int)xpos, (int)ypos, this);

	    
	    // Check if we've arrived at the harbor.
	    if ((harborx == (int)xpos) && (harbory == (int)ypos)) {
	    	this.target_harbor.AddBoat(this);
	    	this.loading = true;
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
	 * NOTE: current_harbor can be null, on first iteration
	 */
	protected HarborAgent CalculateNextHarbor(HarborAgent current_harbor) {
		
		
		//P = itemPrice(toHarbor)*boatCap - boatRiskFactor*uncertainty(atHarbor,toHarbor)-fuelPrice*size*distance(atHarbor,toHarbor)
		//uncertainty(atHarbor,ToHarbor) = distance(atHarbor,toHarbor)*speed+queueSizeConstant*queueSize(toHarbor)
		
		int harbor_id = Random.uniform.nextIntFromTo(0, this.space.GetHarbors().size()-1);
		return this.space.GetHarbors().get(harbor_id);
	}
	
	/**
	 * Method to calculate the expected profit given item & harbor
	 * Boat will travel from buy_harbor -> sell_harbor
	 * @return
	 */
	protected double CalculateExpectedProfit(int item_index, HarborAgent buy_harbor, HarborAgent sell_harbor) {
		
		double profit = sell_harbor.getItems().get(item_index).GetBoat2HarborPrice() * this.size  	// Sell price
						- buy_harbor.getItems().get(item_index).GetHarbor2BoatPrice() * this.size 	// Buy price
				        - this.risk_factory * this.Uncertainty(buy_harbor, sell_harbor) 			// Uncertainty
				        - this.space.GetFuelPrices() * this.size * BoatAgent.HarborDistance(buy_harbor, sell_harbor);  // Fuel prices
				        
		
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
	 * Method to get the unload time of this boat
	 */
	public int getUnloadTime() {
		return (int)this.size;
	}
	
	
	public void setSize(double size) {
		this.size = size;
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
