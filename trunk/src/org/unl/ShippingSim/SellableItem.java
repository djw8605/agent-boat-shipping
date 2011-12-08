package org.unl.ShippingSim;

public class SellableItem {

	protected double harbor2boat_price = 0.0; // the price for the item from harbor to boat
	protected double boat2harbor_price = 0.0; // the price for the item from boat to harbor
	protected double inventory = 0.0; // inventory in current harbor
	protected double inventory_sum = 0.0; //sum inventory in all the harbors
	protected double baseline = 0.0; //base price of the item
	protected double consumption_rate = 0.0; 
	protected double production_rate = 0.0;
	
	/**
	 * Function to initialize SellableItem
	 */
	public SellableItem(double baseline, double starting_inventory, double consumption_rate, double production_rate, double inventory_sum) {
		this.baseline = baseline;
		this.inventory = starting_inventory;
		this.inventory_sum = inventory_sum;
		this.consumption_rate = consumption_rate;
		this.production_rate = production_rate;
	}
	/**
	 * Function to update the price
	 */
	protected void UpdatePrice(){
		//harbor2boat_price = 5*consumption_rate - 3*production_rate - 5*(inventory/consumption_rate)  + baseline;
		harbor2boat_price = Math.cos((inventory/inventory_sum)*Math.PI) * baseline+baseline;
		//harbor2boat_price = Math.tan((1.0-inventory/(inventory_sum+1))*Math.PI/2.0)+baseline*(consumption_rate/production_rate);
		boat2harbor_price = 0.9 * harbor2boat_price;
	}
	
	/**
	 * Function to update the amount
	 */
	protected void UpdateAmount(){
		inventory = inventory + production_rate - consumption_rate;
		if(inventory < 0) inventory = 0;
	}


	/**
	 * Function to update the price and the amount
	 * required to call this function every round
	 */
	public void Update(){
		UpdateAmount();
		UpdatePrice();	
	}
	
	/**
	 * Function to trade items from the harbor to boat
	 * @param n - the amount of item going to trade
	 * @return if the trade is successful, return true, otherwise, return false
	 */
	public boolean BoatBuyItem(double n){
		if(n > inventory) return false;
		inventory -= n;
		UpdatePrice();
		return true;
	}
	
	/**
	 * Function to trade items from the boat to harbor
	 * @param n - the amount of item going to trade
	 * @return the total price for this trade
	 */
	public double HarborBuyItem(double n){
		inventory += n;
		UpdatePrice();
		return n * boat2harbor_price;
	}
	
	public double GetHarbor2BoatPrice(){
		return harbor2boat_price;
	}
	public double GetBoat2HarborPrice(){
		return boat2harbor_price;
	}
	public double GetInventory(){
		return inventory;
	}
}
