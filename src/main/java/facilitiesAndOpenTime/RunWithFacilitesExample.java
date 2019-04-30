package facilitiesAndOpenTime;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkFactory;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Population;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup.ActivityParams;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup.TypicalDurationScoreComputation;
import org.matsim.core.scoring.functions.CharyparNagelOpenTimesScoringFunctionFactory;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.facilities.ActivityFacilities;
import org.matsim.facilities.ActivityFacilitiesFactory;
import org.matsim.facilities.ActivityFacility;
import org.matsim.facilities.ActivityOption;
import org.matsim.facilities.OpeningTimeImpl;

public class RunWithFacilitesExample {

		private Scenario scenario;
		
		public static void main(String[] args) {
			new RunWithFacilitesExample().run();
		}
		
		public void run() {
			final Config config = ConfigUtils.createConfig();
			config.controler().setOverwriteFileSetting(OverwriteFileSetting.deleteDirectoryIfExists);
			config.controler().setLastIteration(0);
			this.scenario = ScenarioUtils.loadScenario(config);
			
			ActivityParams params = new ActivityParams("home");
			params.setTypicalDuration(12*3600);
			params.setTypicalDurationScoreComputation(TypicalDurationScoreComputation.relative);
			config.planCalcScore().addActivityParams(params);
			

			ActivityParams params1 = new ActivityParams("shop");
			params1.setTypicalDuration(1*3600);
			params1.setTypicalDurationScoreComputation(TypicalDurationScoreComputation.relative);
			config.planCalcScore().addActivityParams(params1);
			
			//Creating a network
			Coord defaultCoord = new Coord(.0,.0);
			
			final Network network = scenario.getNetwork();
			final NetworkFactory nf = network.getFactory();
			
			Node node0 = nf.createNode(Id.createNodeId("0"), defaultCoord);
			Node node1 = nf.createNode(Id.createNodeId("1"), new Coord(100.,0.));
			network.addNode(node0);
			network.addNode(node1);
			Link link0_1 = nf.createLink(Id.createLinkId("0-1"),node0,node1);
			Link link1_0 = nf.createLink(Id.createLinkId("1-0"), node1,node0);
			network.addLink(link0_1);
			network.addLink(link1_0);
			
			final ActivityFacilities facilities = scenario.getActivityFacilities();
			final ActivityFacilitiesFactory ff  = facilities.getFactory();
			
			ActivityFacility testFacility = ff.createActivityFacility(Id.create(0, ActivityFacility.class), defaultCoord);
			facilities.addActivityFacility(testFacility);
			{
				ActivityOption ao = ff.createActivityOption("home");
				testFacility.addActivityOption(ao);
				ao.addOpeningTime(new OpeningTimeImpl(0.0*3600, 9999.0*3600));
			}
			{
				ActivityOption ao = ff.createActivityOption("shop");
				testFacility.addActivityOption(ao);
				ao.addOpeningTime(new OpeningTimeImpl(9.0*3600, 10.0*3600));
			}
			
			final Population population = scenario.getPopulation();
			final PopulationFactory pf = population.getFactory();
			
			{
				Person person = pf.createPerson(Id.create(1, Person.class));
				population.addPerson(person);
				
				Plan plan = pf.createPlan();
				person.addPlan(plan);
				{
					Activity act = pf.createActivityFromCoord("home", defaultCoord);
					plan.addActivity(act);
					act.setFacilityId(testFacility.getId());
					act.setEndTime(9.0*3600);
				}
				{
					Activity act = pf.createActivityFromCoord("shop", defaultCoord);
					plan.addActivity(act);
					act.setFacilityId(testFacility.getId());
					act.setEndTime(10.0*3600);
				}
				{
					Activity act = pf.createActivityFromCoord("home", defaultCoord);
					plan.addActivity(act);
					act.setFacilityId(testFacility.getId());
				}
			}
			{
				Person person = pf.createPerson(Id.create(2, Person.class));
				population.addPerson(person);
				
				Plan plan = pf.createPlan();
				person.addPlan(plan);
				{
					Activity act = pf.createActivityFromCoord("home", defaultCoord);
					plan.addActivity(act);
					act.setFacilityId(testFacility.getId());
					act.setEndTime(10.0*3600); // i.e arrive at shop when it closed
				}
				{
					Activity act = pf.createActivityFromCoord("shop", defaultCoord);
					plan.addActivity(act);
					act.setFacilityId(testFacility.getId());
					act.setEndTime(11.0*3600);
				}
				{
					Activity act = pf.createActivityFromCoord("home", defaultCoord);
					plan.addActivity(act);
					act.setFacilityId(testFacility.getId());
				}
			}
			
			Controler controler = new Controler(scenario);
			
			controler.addOverridingModule( new AbstractModule() {
				@Override public void install() {
					CharyparNagelOpenTimesScoringFunctionFactory factory = new CharyparNagelOpenTimesScoringFunctionFactory(scenario);
					this.bindScoringFunctionFactory().toInstance(factory);
				}
			});
			
			controler.run();
			
			for (Person pp : population.getPersons().values()) {
				System.out.println(pp);
				System.out.println(pp.getSelectedPlan());
			}
		}
}
