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
	protected float fuelPrice;
	
	// Impact of long queues
	protected float queueImpact;
	
	// How far in the future the boats will look
	protected int farsight;
	
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
	
	public final static int SPACE_WIDTH = 100;
	public final static int SPACE_HEIGHT = 100;
	
	public ShippingModel() {
		this.params = new String [] {"NumberBoats",
									 "BaselineItem1",
									 "ShipProportion",
									 "FuelPrice",
									 "QueueImpact",
									 "FarSight"};
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
		queue_graph.setXViewPolicy(OpenSequenceGraph.SHOW_LAST);
		queue_graph.setXRange(0, 1000);
		for (int i = 0; i < space.GetHarbors().size(); i++) {
			queue_graph.createSequence("Harbor " + Integer.toString(i), space.GetHarbors().get(i), "getBoatNum");
			//queue_graph.addSequence("Harbor " + Integer.toString(i), new HarborQueueSeq(i, space));
		}
	}
	
	@SuppressWarnings("unchecked")
	public void buildModel() {
		// Initialize the simulation space
		space = new OceanSpace(ShippingModel.SPACE_WIDTH, ShippingModel.SPACE_HEIGHT);
		
		// Add new boats & harbors to the agentlist
		
		// Place the boats
		for (int i = 0; i < this.numBoats; i++) {
			  int x, y;
			  do {
			    x = Random.uniform.nextIntFromTo(0, space.getSizeX() - 1);
			    y = Random.uniform.nextIntFromTo(0, space.getSizeY() - 1);
			  } while (space.getObjectAt(x, y) != null);

			  BoatAgent agent = boatfactory.CreateBoat(x, y, space);
			  space.putObjectAt(x, y, agent);
			  agentList.add(agent);
			}
		
		// Place and add the Harbors
		ArrayList<HarborAgent> harbors = harborfactory.CreateHarbors();
		for (int i=0; i< harbors.size(); i++){
			agentList.add(harbors.get(i));
			space.AddHarbor(harbors.get(i));
		}
		
		buildDisplay();
		this.queue_graph.display();
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
	

}
