package org.concac.learning.tutorial;
import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.run.Controler;

public class RunExample5 {
	public static void main(String [] args) {
		String configFile = "examples/tutorial/config/example5-config.xml";
		 Controler controler = new Controler(configFile);
		 controler.run();
		 
		 Scenario sc = controler.getScenario();
		 Config cf = sc.getConfig();
		 String dir = cf.controler().getOutputDirectory();
		
	}
}
