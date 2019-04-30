package external;

import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.config.groups.ExternalMobimConfigGroup;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.controler.Controler;

public class RunExternalMobsimExample {
	public static void main(String [] args) {
		String configFile;
		if(args != null && args.length >= 1) {
			configFile = args[0];
		}
		else {
			configFile = "examples/tutorial/config/externalMobsim.xml";
		}
		Config config = ConfigUtils.loadConfig(configFile, new ExternalMobimConfigGroup());
		Scenario scenario = ScenarioUtils.loadScenario(config);
		
		Controler  controler = new Controler(scenario);
		controler.run();
	}
}
