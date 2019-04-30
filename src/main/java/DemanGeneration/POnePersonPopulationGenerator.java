package DemanGeneration;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Population;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.api.core.v01.population.PopulationWriter;
import org.matsim.core.api.internal.MatsimWriter;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.CoordinateTransformation;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;


public class POnePersonPopulationGenerator {
	public static void main(String [] args) {
		CoordinateTransformation ct = TransformationFactory.getCoordinateTransformation(TransformationFactory.WGS84, TransformationFactory.WGS84_UTM33N);
		
		// loading a config and scenario
		Config config = ConfigUtils.createConfig();
		Scenario sc   = ScenarioUtils.createScenario(config);
		
		// loading population and network
		Network network = sc.getNetwork();
		Population population = sc.getPopulation();
		
		// Pick the populationfactory out off the Population for convience
		PopulationFactory populationFactory = population.getFactory();
		
		//Creating 1 person designated "1" and add it to the population
		Person person = populationFactory.createPerson(Id.create("1", Person.class));
		population.addPerson(person);
		
		// getting plan for that person
		Plan plan = populationFactory.createPlan();
		
		Coord homeCoordinates = new Coord(14.31377,51.76948);
		
		Activity activity1 = populationFactory.createActivityFromCoord("home",ct.transform(homeCoordinates));
		activity1.setEndTime(21600);
		plan.addActivity(activity1);
		
		plan.addLeg(populationFactory.createLeg("car"));
		
		// creating a work activity
		Activity activity2 = populationFactory.createActivityFromCoord("work", ct.transform(new Coord(14.34024, 51.75649)));
		activity2.setEndTime(57600); // 8 hours
		plan.addActivity(activity2);
		
		plan.addLeg(populationFactory.createLeg("car"));


		Activity activity3 = populationFactory.createActivityFromCoord("home",ct.transform(homeCoordinates));
		plan.addActivity(activity3);
		person.addPlan(plan);
		
		/*
		 * Write the plan of 1 person to xml
		 * 
		 * */
		MatsimWriter popWriter = new PopulationWriter(population, network);
		popWriter.write("./input/population.xml");
		
	}
}
