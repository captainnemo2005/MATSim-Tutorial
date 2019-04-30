package facilities;

import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.gbl.Gbl;
import org.matsim.core.network.NetworkUtils;
import org.matsim.api.core.v01.network.Link;
import org.matsim.facilities.ActivityFacility;
import org.matsim.facilities.ActivityFacilityImpl;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.api.core.v01.Coord;

public class RunAddLinkIDsToFacilitiesExample {
	
	public static void main(String [] args) {
		Config config = ConfigUtils.loadConfig("scenarios/equil/config.xml");
		config.controler().setOverwriteFileSetting(OverwriteFileSetting.deleteDirectoryIfExists);
		
		Scenario scenario = ScenarioUtils.loadScenario(config);
		
		for(ActivityFacility fac : scenario.getActivityFacilities().getFacilities().values()) {
			if(fac.getLinkId() == null) {
				final Coord coord = fac.getCoord();
				Gbl.assertNotNull(coord);
				Link link = NetworkUtils.getNearestLink(scenario.getNetwork(), coord);
				Gbl.assertNotNull(coord);
				((ActivityFacilityImpl)fac).setLinkId(link.getId());
			}
		}
	}
}
