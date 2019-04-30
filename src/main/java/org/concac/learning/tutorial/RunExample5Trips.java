package org.concac.learning.tutorial;
import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.run.Controler;

public class RunExample5Trips {
	private static Logger log = Logger.getLogger(RunExample5Trips.class); 
	public static void main(String [] args) {
		String configFile = "C:\\Users\\kngu0033\\eclipse-workspace\\tutorial\\examples\\tutorial\\config\\example5trips-config.xml";
		Controler controler = new Controler(configFile);
		controler.run();
		
		Scenario sc = controler.getScenario();
		Config cf = sc.getConfig();
		String dir = cf.controler().getOutputDirectory();
		log.warn("Output is in " + dir + ".");
	}
}
