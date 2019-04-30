package MyEventHandler;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.vehicles.Vehicle;

import java.util.Map;
import java.util.HashMap;

public class CongestionDetectionEventHandler implements LinkEnterEventHandler,
						LinkLeaveEventHandler, PersonDepartureEventHandler	{
	private Map<Id<Vehicle>,Double> earliestLinkExitTime = new HashMap<Id<Vehicle>,Double>();
	private Network network;
	
	public CongestionDetectionEventHandler(Network network) {
		this.network = network;
	}
	
	//@Override
	public void reset(int iteration) {
		this.earliestLinkExitTime.clear();
	}
	
	//@Override
	public void handleEvent(LinkEnterEvent event) {
		Link link = network.getLinks().get(event.getLinkId());
		double linkTravelTime = link.getLength()/ link.getFreespeed(event.getTime());
		this.earliestLinkExitTime.put(event.getVehicleId(),event.getTime() + linkTravelTime);
	}
	
	public void handleEvent(LinkLeaveEvent event) {
		double excessTravelTime = event.getTime() - this.earliestLinkExitTime.get(event.getVehicleId());
		System.out.println("excess travel time: " + excessTravelTime);
	}
	
	public void handleEvent(PersonDepartureEvent event) {
		Id<Vehicle> vehId = Id.create(event.getPersonId(), Vehicle.class);
		this.earliestLinkExitTime.put(vehId,event.getTime());
	}
}
