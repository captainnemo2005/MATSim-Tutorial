package roadPricing;

import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;
import roadPricing.RoadPricingConfigGroup;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;

public class RunRoadPricingExample {
	
	public static String configFile = "scenarios/equil-extended/config-with-roadpricing.xml";
	
	public static void main(String[] args) {
		Config config = ConfigUtils.loadConfig(configFile, new RoadPricingConfigGroup());
		config.controler().setOverwriteFileSetting(OverwriteFileSetting.deleteDirectoryIfExists);
		Scenario scenario = ScenarioUtils.loadScenario(config);
		
		Controler controler = new Controler(scenario);
		controler.run();
	}
}
