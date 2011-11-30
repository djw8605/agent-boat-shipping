package org.unl.ShippingSim;

import java.awt.Color;
import java.util.ArrayList;

import uchicago.src.sim.analysis.OpenSequenceGraph;
import uchicago.src.sim.engine.SimpleModel;
import uchicago.src.sim.gui.DisplaySurface;
import uchicago.src.sim.gui.Object2DDisplay;
import uchicago.src.sim.util.Random;

public class ShippingModel extends SimpleModel {

	protected int numBoats;
	
	// Baseline values for items
	protected float baselineItem1;
	
	// Proportion of ships that are 'big'
	protected float shipSizeProportion;
	
	// Fuel price
	protected float fuelPrice = (float)1.0;
	
	// Impact of long queues
	protected float queueImpact = (float)1.0;
	
	// How far in the future the boats will look
	protected int farsight = 1;
	
	// The simulation space
	protected OceanSpace space;
	
	// The display surface
	protected DisplaySurface dsurf;
	
	// Boat Factory
	protected BoatFactory boatfactory;
	
	// Harbor Factory
	protected HarborFactory harborfactory;
	
	// Harbor queue graphs
	protected OpenSequenceGraph queue_graph;
	
	// Item Price Graphs
	protected OpenSequenceGraph[] price_graphs ={ 	new OpenSequenceGraph("Harbor 1 Prices", this), 
													new OpenSequenceGraph("Harbor 2 Prices", this),
													new OpenSequenceGraph("Harbor 3 Prices", this), 
													new OpenSequenceGraph("Harbor 4 Prices", this)};
	
	// Harbor per boat profit graphs
	protected OpenSequenceGraph per_boatsize_profit_graph;
	
	// Harbor avg profit graphs
	protected OpenSequenceGraph avg_profit_graph;
		
	// Harbor queue graphs
	protected OpenSequenceGraph total_profit_graph;
	
	// Number of small boats
	protected int numSmallBoats = 50;
	
	// Number of medium boats
	protected int numMediumBoats = 50;
	
	// Number of large boats
	protected int numLargeBoats = 50;
	
	// The center of the risk distribution
	protected double risk_center = 1.0;
	
	// Range of the risk distribution (normal distribution)
	protected double risk_range = 1.0;
	
	public final static int SPACE_WIDTH = 100;
	public final static int SPACE_HEIGHT = 100;
	
	public ShippingModel() {
		this.params = new String [] {"FuelPrice",
									 "QueueImpact",
									 "FarSight",
									 "SmallBoats",
									 "MediumBoats",
									 "LargeBoats",
									 "RiskCenter",
									 "RiskRange"};
		this.name = "Shipping Simulation";
		
		boatfactory = new BoatFactory();
		harborfactory = new HarborFactory(SPACE_WIDTH, SPACE_HEIGHT);
		
		
	}
	
	public void setup() {
		super.setup();
		
		dsurf = new DisplaySurface(this, "Ocean");
		// Initialize the boat & Harbor factories
		
	}
	
	public void buildDisplay() {

		Object2DDisplay agentDisplay = new Object2DDisplay(this.space);
		agentDisplay.setObjectList(agentList);
		

		dsurf.addDisplayableProbeable(agentDisplay, "Agents");
		addSimEventListener(dsurf);
		dsurf.setBackground(Color.blue);
		
		queue_graph = new OpenSequenceGraph("Harbor Queues", this);
		queue_graph.setXViewPolicy(OpenSequenceGraph.SHOW_ALL);
		//queue_graph.setXRange(0, 1000);
		for (int i = 0; i < space.GetHarbors().size(); i++) {
			queue_graph.createSequence("Harbor " + Integer.toString(i), space.GetHarbors().get(i), "getBoatNum");
			//queue_graph.addSequence("Harbor " + Integer.toString(i), new HarborQueueSeq(i, space));
		}
		
		//price_graphs = new OpenSequenceGraph[4];
		for (int i = 0; i < space.GetHarbors().size(); i++) {
			for (int a = 0; a < space.GetHarbors().get(i).getItems().size(); a++) {
				SellableItem item = space.GetHarbors().get(i).getItems().get(a);
				price_graphs[i].setXViewPolicy(OpenSequenceGraph.SHOW_ALL);
				//price_graphs[i].setXRange(0, 1000);
				//price_graphs[i].createSequence("Harbor " + Integer.toString(i) + ": Item " + Integer.toString(a), item, "GetInventory");
				price_graphs[i].createSequence("Harbor " + Integer.toString(i) + ": Item " + Integer.toString(a), item, "GetHarbor2BoatPrice");
			}
		}
		
		// Graph initialization stuff...
		this.per_boatsize_profit_graph = new OpenSequenceGraph("Per Boat Profit", this);
		this.per_boatsize_profit_graph.setXViewPolicy(OpenSequenceGraph.SHOW_ALL);
		this.per_boatsize_profit_graph.createSequence("Small", this.boatfactory, "getSmallBoatProfit");
		this.per_boatsize_profit_graph.createSequence("Medium", this.boatfactory, "getMediumBoatProfit");
		this.per_boatsize_profit_graph.createSequence("Large", this.boatfactory, "getLargeBoatProfit");
		
		this.total_profit_graph = new OpenSequenceGraph("Total Boat Profit", this);
		this.total_profit_graph.setXViewPolicy(OpenSequenceGraph.SHOW_ALL);
		this.total_profit_graph.createSequence("All Boats", this.boatfactory, "getBoatProfit");
		
		this.avg_profit_graph = new OpenSequenceGraph("Avg Boat Profit", this);
		this.avg_profit_graph.setXViewPolicy(OpenSequenceGraph.SHOW_ALL);
		this.avg_profit_graph.createSequence("Small", this.boatfactory, "getSmallBoatAvgProfit");
		this.avg_profit_graph.createSequence("Medium", this.boatfactory, "getMediumBoatAvgProfit");
		this.avg_profit_graph.createSequence("Large", this.boatfactory, "getLargeBoatAvgProfit");
		
	}
	
	@SuppressWarnings("unchecked")
	public void buildModel() {
		// Initialize the simulation space
		Random.createUniform();
		space = new OceanSpace(ShippingModel.SPACE_WIDTH, ShippingModel.SPACE_HEIGHT);
		
		// Add the attributes to the model
		boatfactory.SetQueueEffect(this.queueImpact);
		boatfactory.setFarSight(farsight);
		
		// Add new boats & harbors to the agentlist
		
		
		
		// Place and add the Harbors
		ArrayList<HarborAgent> harbors = harborfactory.CreateHarbors();
		for (int i=0; i< harbors.size(); i++){
			agentList.add(harbors.get(i));
			space.AddHarbor(harbors.get(i));
		}
		
		// Place the boats
		// Small boats
		for (int i = 0; i < this.numSmallBoats; i++) {
			HarborAgent init_harbor = harbors.get(Random.uniform.nextIntFromTo(0, harbors.size()-1));
			int x = init_harbor.getX();
			int y = init_harbor.getY();
			BoatAgent agent = boatfactory.CreateBoat(x, y, space, init_harbor, BoatFactory.SMALL_BOAT);
			space.putObjectAt(x, y, agent);
			agentList.add(agent);
		}
		
		// Medium boats
		for (int i = 0; i < this.numMediumBoats; i++) {
			HarborAgent init_harbor = harbors.get(Random.uniform.nextIntFromTo(0, harbors.size()-1));
			int x = init_harbor.getX();
			int y = init_harbor.getY();
			BoatAgent agent = boatfactory.CreateBoat(x, y, space, init_harbor, BoatFactory.MEDIUM_BOAT);
			space.putObjectAt(x, y, agent);
			agentList.add(agent);
		}
		
		// Large boats
		for (int i = 0; i < this.numLargeBoats; i++) {
			HarborAgent init_harbor = harbors.get(Random.uniform.nextIntFromTo(0, harbors.size()-1));
			int x = init_harbor.getX();
			int y = init_harbor.getY();
			BoatAgent agent = boatfactory.CreateBoat(x, y, space, init_harbor, BoatFactory.LARGE_BOAT);
			space.putObjectAt(x, y, agent);
			agentList.add(agent);
		}
		

		buildDisplay();
		this.queue_graph.display();
		for(int i = 0; i < this.price_graphs.length; i++)
			this.price_graphs[i].display();
		
		this.per_boatsize_profit_graph.display();
		this.total_profit_graph.display();
		this.avg_profit_graph.display();
		dsurf.display();
	}

	private int stepper = 0;
	public void step() {
		int size = agentList.size();
		for (int i = 0; i < size; i++) {
			AbstractAgent agent = (AbstractAgent) agentList.get(i);
			agent.step();
			
		}
		
		// Update the graphs
		if ((stepper % 100) == 0) {
			stepper = 0;
			this.queue_graph.step();
			for (int i = 0; i < this.price_graphs.length; i++)
				this.price_graphs[i].step();
			this.per_boatsize_profit_graph.step();
			this.total_profit_graph.step();
			this.avg_profit_graph.step();
		}
		stepper++;
		
		
		dsurf.updateDisplay();
	}
	
	
	/* Parameter functions */
	
	// Number of boats
	public void setNumberBoats(int val) {
		this.numBoats = val;
	}
	public int getNumberBoats() {
		return this.numBoats;
	}
	
	// Baseline values
	public void setBaselineItem1(float val) {
		this.baselineItem1 = val;
	}
	public float getBaselineItem1() {
		return this.baselineItem1;
	}
	
	// Proportion of ship sizes
	public void setShipProportion(float val) {
		this.shipSizeProportion = val;
	}
	public float getShipProportion() {
		return this.shipSizeProportion;
	}
	
	// Fuel prices
	public void setFuelPrice(float val) {
		this.fuelPrice = val;
	}
	public float getFuelPrice() {
		return this.fuelPrice;
	}
	
	// Impact of queues
	public void setQueueImpact(float val) {
		this.queueImpact = val;
	}
	public float getQueueImpact() {
		return this.queueImpact;
	}
	
	// How far in the future the boat will look (far sight)
	public void setFarSight(int val) {
		this.farsight = val;
	}
	public int getFarSight() {
		return this.farsight;
	}
	
	// Set number of small boats
	public void setSmallBoats(int val) {
		this.numSmallBoats = val;
	}
	public int getSmallBoats() {
		return this.numSmallBoats;
	}
	
	// Set number of medium boats
	public void setMediumBoats(int val) {
		this.numMediumBoats = val;
	}

	public int getMediumBoats() {
		return this.numMediumBoats;
	}

	// Set number of large boats
	public void setLargeBoats(int val) {
		this.numLargeBoats = val;
	}

	public int getLargeBoats() {
		return this.numLargeBoats;
	}
	
	// Center of boat's risk distribution
	public void setRiskCenter(double val) {
		this.risk_center = val;
	}
	
	public double getRiskCenter() {
		return this.risk_center;
	}
	
	public void setRiskRange(double val) {
		this.risk_range = val;
	}
	
	public double getRiskRange() {
		return this.risk_range;
	}
	
	
	// Baseline values
	
	
	

}
