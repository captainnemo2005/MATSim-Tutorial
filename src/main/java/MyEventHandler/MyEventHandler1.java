package MyEventHandler;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;

public class MyEventHandler1 implements LinkEnterEventHandler,LinkLeaveEventHandler, PersonArrivalEventHandler,PersonDepartureEventHandler{

	public void reset(int iteration) {
		System.out.println("reset...");
	}
	

	public void handleEvent(LinkEnterEvent event) {
		System.out.println("LinkEnterEvent ");
		System.out.println("Time "+event.getTime());
		System.out.println("LinkEnterEvent " + event.getLinkId());
	}

	public void handleEvent(LinkLeaveEvent event) {
		System.out.println("LinkLeaveEvent");
		System.out.println("Time: " + event.getTime());
		System.out.println("LinkId: " + event.getLinkId());
		//System.out.println("PersonId: " + event.getPersonId());
	}


	public void handleEvent(PersonArrivalEvent event) {
		System.out.println("AgentArrivalEvent");
		System.out.println("Time: " + event.getTime());
		System.out.println("LinkId: " + event.getLinkId());
		System.out.println("PersonId: " + event.getPersonId());
	}


	public void handleEvent(PersonDepartureEvent event) {
		System.out.println("AgentDepartureEvent");
		System.out.println("Time: " + event.getTime());
		System.out.println("LinkId: " + event.getLinkId());
		System.out.println("PersonId: " + event.getPersonId());
	}
}

