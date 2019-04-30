package ControListener;
import org.matsim.core.controler.Controler;
public class MyMainClass {
	public static void main(String args []) {
		String configFile = "examples/programming/example7-config.xml";
		Controler controler = new Controler(configFile);
		int popSize = controler.getScenario().getPopulation().getPersons().size();
		System.out.println(popSize);
		controler.addControlerListener(new MyControlerListener(popSize));
		controler.run();
	}
}
