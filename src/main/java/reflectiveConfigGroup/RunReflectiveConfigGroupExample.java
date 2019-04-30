package reflectiveConfigGroup;

import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.ControlerUtils;

public class RunReflectiveConfigGroupExample {
	public static void main(String [] args) {
		String configFile = "scenarios/equil/config.xml";
		Config  config = ConfigUtils.loadConfig(configFile, new MyConfigGroup());
		
		MyConfigGroup myConfigGroup = ConfigUtils.addOrGetModule(config, MyConfigGroup.GROUP_NAME, MyConfigGroup.class);
		
		myConfigGroup.setDoubleField(-99.13);
		
		config.checkConsistency();
		
		ControlerUtils.checkConfigConsistencyAndWriteToLog(config, "test");
	}
}
