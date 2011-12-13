package org.unl.ShippingSim;

import java.util.ArrayList;

import uchicago.src.sim.util.Random;


public class BoatFactory {

	protected float queueeffect = 0;
	
	public enum Distributions { NORMAL, UNIFORM, BINOMIAL, LOG, POISSON };
	
	public enum BoatSizes { SMALL, MEDIUM, LARGE };
	
	public static final double SMALL_BOAT = 50.0;
	public static final double MEDIUM_BOAT = 150.0;
	public static final double LARGE_BOAT = 300.0;
	
	protected Distributions uncertainty_distrubtion;
	
	// Arrays to aggregate profits from different subsets of boats
	protected ArrayList<BoatAgent> all_boats = new ArrayList<BoatAgent>();
	protected ArrayList<BoatAgent> small_boats = new ArrayList<BoatAgent>();
	protected ArrayList<BoatAgent> medium_boats = new ArrayList<BoatAgent>();
	protected ArrayList<BoatAgent> large_boats = new ArrayList<BoatAgent>();
	
	protected int farsight = 1;
	
	// Center of risk distribution
	protected double risk_center;
	protected double risk_range;
	
	// Initialization
	public BoatFactory() {
		
		
		
	}
	
	
	/**
	 * Create a boat
	 * @param x - int x location
	 * @param y - int y location
	 * @param space - Global space for a global view.  Only contains a limited view.
	 * @param init_harbor - Initial harbor
	 * @param boat_size - starting size of the boat.  Should be one of the constants defined above.
	 * @return
	 */
	public BoatAgent CreateBoat(int x, int y, OceanSpace space, HarborAgent init_harbor, double boat_size) {
		BoatAgent b = new BoatAgent(x, y, space);
		// Do the initialization stuff
		float calculated_boat_size = Random.uniform.nextFloatFromTo((float)boat_size - 20, (float)boat_size + 20);
		b.setSize(calculated_boat_size);
		b.SetHarbor(init_harbor);
		b.setQueueEffect(this.queueeffect);
		b.setFarsight(this.farsight);
		double risk = Random.uniform.nextDoubleFromTo(this.risk_center - this.risk_range, this.risk_center + this.risk_range);
		b.setUncertainty(risk);
		
		// Add the boats for profit aggregation (graphing)
		this.all_boats.add(b);
		if (boat_size == SMALL_BOAT) {
			b.setSizeEnum(BoatSizes.SMALL);
			this.small_boats.add(b);
		} else if (boat_size == MEDIUM_BOAT) {
			b.setSizeEnum(BoatSizes.MEDIUM);
			this.medium_boats.add(b);
		} else if (boat_size == LARGE_BOAT) {
			b.setSizeEnum(BoatSizes.LARGE);
			this.large_boats.add(b);
		}
		return b;
		
	}
	
	/**
	 * Set the queue effect
	 * @param queueeffect
	 */
	public void SetQueueEffect(float queueeffect) {
		this.queueeffect = queueeffect;
	}
	
	/**
	 * Set the farsight of the boats
	 * @param farsight
	 */
	public void setFarSight(int farsight) {
		this.farsight = farsight;
	}
	
	/**
	 * Set the boat risk factor and range
	 */
	public void setRiskCenter(double risk_center, double risk_range) {
		this.risk_center = risk_center;
		this.risk_range = risk_range;
	}
	
	/**
	 * Get the total profit from small boats
	 */
	public double getSmallBoatProfit() {
		double total_profit = 0.0;
		for (int i = 0; i < this.small_boats.size(); i++)
			total_profit += this.small_boats.get(i).getMoney();
		return total_profit;
	}
	
	/**
	 * Get the total profit from medium boats
	 */
	public double getMediumBoatProfit() {
		double total_profit = 0.0;
		for (int i = 0; i < this.medium_boats.size(); i++)
			total_profit += this.medium_boats.get(i).getMoney();
		return total_profit;
	}
	
	/**
	 * Get the total profit from large boats
	 */
	public double getLargeBoatProfit() {
		double total_profit = 0.0;
		for (int i = 0; i < this.large_boats.size(); i++)
			total_profit += this.large_boats.get(i).getMoney();
		return total_profit;
	}
	
	/**
	 * Get the total profit from all boats
	 */
	public double getBoatProfit() {
		double total_profit = 0.0;
		for (int i = 0; i < this.all_boats.size(); i++)
			total_profit += this.all_boats.get(i).getMoney();
		return total_profit;
	}
	
	
	/**
	 * Get the average profit from small boats
	 */
	public double getSmallBoatAvgProfit() {
		double total_profit = 0.0;
		for (int i = 0; i < this.small_boats.size(); i++)
			total_profit += this.small_boats.get(i).getMoney();
		return total_profit / (double)this.small_boats.size();
		
	}

	/**
	 * Get the average profit from medium boats
	 */
	public double getMediumBoatAvgProfit() {
		double total_profit = 0.0;
		for (int i = 0; i < this.medium_boats.size(); i++)
			total_profit += this.medium_boats.get(i).getMoney();
		return total_profit / (double)this.medium_boats.size();
	}
	
	/**
	 * Get the average profit from large boats
	 */
	public double getLargeBoatAvgProfit() {
		double total_profit = 0.0;
		for (int i = 0; i < this.large_boats.size(); i++)
			total_profit += this.large_boats.get(i).getMoney();
		return total_profit / (double) this.large_boats.size();
	}
	

	/**
	 * Get the average harbors visited
	 * @return array: [small, medium, large]
	 */
	public double[] getAverageHarborsVisited() {
		double [] average_harbors = {0.0, 0.0, 0.0};
		for(int i = 0; i < this.small_boats.size(); i++)
			average_harbors[0] += this.small_boats.get(i).getHarborsVisited();
		average_harbors[0] /= this.small_boats.size();
		
		for(int i = 0; i < this.medium_boats.size(); i++)
			average_harbors[1] += this.medium_boats.get(i).getHarborsVisited();
		average_harbors[1] /= this.medium_boats.size();
		
		for(int i = 0; i < this.large_boats.size(); i++)
			average_harbors[2] += this.large_boats.get(i).getHarborsVisited();
		average_harbors[2] /= this.large_boats.size();
		
		return average_harbors;
	}
	
	

	
	
}
