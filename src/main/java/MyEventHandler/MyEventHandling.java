package MyEventHandler;

import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.controler.Controler;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.api.experimental.events.EventsManager;

public class MyEventHandling {
	public static void main(String [] args) {
		Config config = ConfigUtils.loadConfig("scenarios/equil/config.xml");
		config.controler().setLastIteration(5);
		Scenario scenario = ScenarioUtils.loadScenario(config);
		Controler controler= new Controler(scenario);
		
		String inputFile = "output/example5/output_events.xml.gz";
		
		EventsManager events = EventsUtils.createEventsManager();
		
		MyEventHandler1 handler1 = new MyEventHandler1();
		MyEventHandler2 handler2 = new MyEventHandler2();
		MyEventHandler3 handler3 = new MyEventHandler3();
		events.addHandler(handler1);
		events.addHandler(handler2);
		events.addHandler(handler3);
		
		MatsimEventsReader reader = new MatsimEventsReader(events);
		reader.readFile(inputFile);
		
		System.out.println("average travel time: " + handler2.getTotalTravelTime());
		handler3.writeChart("output/departurePerHour.png");
		
		System.out.println("Events file reader! ");
		}
}
