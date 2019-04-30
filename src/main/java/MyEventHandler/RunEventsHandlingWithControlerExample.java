package MyEventHandler;

import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.AbstractModule;
//import org.matsim.core.controler.ControlerUtils;
import org.matsim.api.core.v01.Scenario;
public class RunEventsHandlingWithControlerExample {
	
	public static final String outputDirectory = "output/example7";
	
	public static void main(String args[]) {
		String inputFile = "scenarios/equil/config-with-controlerListener.xml";
		Config config = ConfigUtils.loadConfig(inputFile);
		config.controler().setOutputDirectory(outputDirectory);
		
		final Scenario scenario = ScenarioUtils.loadScenario(config);
		Controler controler = new Controler(scenario);
		
		controler.addOverridingModule(new AbstractModule(){
			@Override public void install() {
				this.addEventHandlerBinding().toInstance(new MyEventHandler1());
				this.addEventHandlerBinding().toInstance(new MyEventHandler2());
				this.addEventHandlerBinding().toInstance(new MyEventHandler3());
				this.addEventHandlerBinding().toInstance(new CongestionDetectionEventHandler(scenario.getNetwork() ) ) ;
			}
		});
		
		controler.run();
	}
}
