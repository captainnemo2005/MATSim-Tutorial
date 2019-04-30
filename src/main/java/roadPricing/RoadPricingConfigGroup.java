package roadPricing;

import java.util.Map;

import org.matsim.core.config.ReflectiveConfigGroup;

public class RoadPricingConfigGroup extends ReflectiveConfigGroup{
	
		public static final String GROUP_NAME = "roadpricing";
		
		private static final String TOLL_LINKS_FILE = "tollLinksFile";
		
		private String tollLinksFile = null;
		
		public RoadPricingConfigGroup() {
			super(GROUP_NAME);
		}
		
		@Override 
		public Map<String,String> getComments(){
			Map<String, String> map = super.getComments();
			return map;
		}
		
		@StringGetter(TOLL_LINKS_FILE)
		public String getTollLinksFile() {
			return this.tollLinksFile;
		}
		
		@StringSetter(TOLL_LINKS_FILE)
		public void setTollLinksFile(String tollLinksFile) {
			this.tollLinksFile = tollLinksFile;
		}
}
