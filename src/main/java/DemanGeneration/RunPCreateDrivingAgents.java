package DemanGeneration;

import org.matsim.core.config.Config;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.population.Population;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup.ActivityParams;
import org.matsim.core.network.io.MatsimNetworkReader;


public class RunPCreateDrivingAgents {
	
		public static void main(String [] args) {
			Config config = ConfigUtils.createConfig();
			
			config.controler().setLastIteration(0);
			
			ActivityParams home = new ActivityParams("home");
			home.setTypicalDuration(16*3600);
			config.planCalcScore().addActivityParams(home);
			
			ActivityParams work = new ActivityParams("work");
			work.setTypicalDuration(8*3600);
			config.planCalcScore().addActivityParams(work);
			
			Scenario scenario = ScenarioUtils.createScenario(config);
			
			new MatsimNetworkReader(scenario.getNetwork()).readFile("./input/network.xml");
			
			fillScenario(scenario);
			
			Controler controler = new Controler(scenario);
			controler.getConfig().controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.overwriteExistingFiles);
			controler.run();
			
		}
		
		private static Population fillScenario(Scenario scenario) {
			Population population = scenario.getPopulation();
			
			for (int i = 0 ; i < 100 ; i++) {
				Coord coord = new Coord((double) (454941 + i * 10), (double) (5737814 + i * 10));
				Coord coordWork = new Coord((double) (454941 - i * 10), (double) (5737814 - i * 10));
				createOnePerson(scenario,population,i,coord,coordWork);
			}
			return population;
		}
		
		private static void createOnePerson(Scenario scenario, Population population, int i ,Coord coord, Coord workCoord) {
				Person person = population.getFactory().createPerson(Id.createPersonId("p_" + i));
			
				Plan plan = population.getFactory().createPlan();
				
				Activity home = population.getFactory().createActivityFromCoord("home",coord);
				home.setEndTime(9*3600);
				plan.addActivity(home);
				
				Leg hinweg = population.getFactory().createLeg("car");
				plan.addLeg(hinweg);
				
				Activity work = population.getFactory().createActivityFromCoord("work",workCoord);
				work.setEndTime(17*3600);
				plan.addActivity(work);
				
				Leg rueckweg = population.getFactory().createLeg("mode");
				plan.addLeg(rueckweg);
				
				person.addPlan(plan);
				population.addPerson(person);
		}
}
