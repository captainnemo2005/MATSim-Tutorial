package MyEventHandler;


import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.VehicleEntersTrafficEvent;
import org.matsim.api.core.v01.events.VehicleLeavesTrafficEvent;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.api.core.v01.events.handler.VehicleEntersTrafficEventHandler;
import org.matsim.api.core.v01.events.handler.VehicleLeavesTrafficEventHandler;

public class MyEventHandler2 implements VehicleEntersTrafficEventHandler,VehicleLeavesTrafficEventHandler, PersonArrivalEventHandler,
											PersonDepartureEventHandler{
	private double timePersonOnTravel = 0.0;
	
	private double timeTrafficOnTravel = 0.0;
	
	public double getTotalTravelTime() {
		return this.timePersonOnTravel;
	}
	
	public double getTotalVehicleInTrafficTime() {
		return this.timeTrafficOnTravel;
	}
	//@Override
	public void reset(int iteration) {
		this.timePersonOnTravel = 0.0;
		this.timeTrafficOnTravel = 0.0;
	}
	
	public void handleEvent(VehicleEntersTrafficEvent event) {
		this.timeTrafficOnTravel -= event.getTime();
	}
	
	public void handleEvent(VehicleLeavesTrafficEvent event) {
		this.timeTrafficOnTravel += event.getTime();
	}
	
	public void handleEvent(PersonArrivalEvent event) {
		this.timePersonOnTravel -= event.getTime();
	}

	public void handleEvent(PersonDepartureEvent event) {
		this.timePersonOnTravel += event.getTime();
	}
}