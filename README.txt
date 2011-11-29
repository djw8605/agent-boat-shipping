CSCE 875 Multi-agent systems project.

Project webpage: https://code.google.com/p/agent-boat-shipping/

Source is available from svn at:
https://agent-boat-shipping.googlecode.com/svn/trunk/

This project is a shipping market simulation.  Boats travel from harbor to
harbor in order to sell godds for a profit.

Documentation to install the project into eclipse can be found on the project 
website:
https://code.google.com/p/agent-boat-shipping/wiki/InstallingProject

The simulation is broken into 8 classes.

Classes:
 AbstractAgent 	Abstract class that both the harbor and boat agents are subclassed.
 BoatAgent  	Agent representing the boat.  This agent travels from harbor to 
 			 	harbor selling goods.
 BoatFactory  	Creates the boats, initializing variables.
 HarborAgent  	Agent that handles buying and selling items to the boats.
 HarborFactory  Used to create the harbors and initialize values.
 OceanSpace  	Global view of the environment.  Very few things are visible, such
 			  	as the harbors and the fuel prices.
 SellableItem	Class that represents an item that a boat can buy/sell.
 ShippingModel	Simulation class that creates the simulation variables and graphs.

