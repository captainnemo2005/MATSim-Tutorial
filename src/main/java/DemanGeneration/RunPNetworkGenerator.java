package DemanGeneration;

import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.network.io.NetworkWriter;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.CoordinateTransformation;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;
import org.matsim.core.utils.io.OsmNetworkReader;
import org.matsim.core.network.algorithms.NetworkCleaner;

public class RunPNetworkGenerator {
	public static void main(String [] args) {
		String osm = "./input/my-map.osm";
		/*
		 * The coordinate system to use OSM is WGS84 but in MATSim they use a projection where
		 * distance are euclidena distances in meters
		 * */
		CoordinateTransformation ct = TransformationFactory.getCoordinateTransformation(TransformationFactory.WGS84, TransformationFactory.WGS84_UTM33N);
		/*
		 * We always has to create config and scenario while working with MATSim 
		 * container
		 * */
		
		Config config = ConfigUtils.createConfig();
		Scenario scenario = ScenarioUtils.createScenario(config);
		
		// pick the network from scenario for convinience
		Network network = scenario.getNetwork();
		
		//Loading the OSM to that network
		OsmNetworkReader onr = new OsmNetworkReader(network,ct);
		onr.parse(osm);
		/*
		 * Clean the network. Cleaning means that remove all disconnected component, so that 
		 * afterward there is a route from every link to every other link.
		 * */
		new NetworkCleaner().run(network);
		
		new NetworkWriter(network).write("./input/network.xml");
	}
}
