package baseballRecord;

import java.util.TreeMap;

public class PitcherMap {
	
	TreeMap<String, PitcherData> pmap;
	
	public PitcherMap() {
		pmap = new TreeMap<>();
	}
	
	public void setPitcherMap(TreeMap<String, PitcherData> pmap) {
		this.pmap = pmap;
		
	}

}
