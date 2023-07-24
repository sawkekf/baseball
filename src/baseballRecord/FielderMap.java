package baseballRecord;

import java.util.TreeMap;

public class FielderMap {
	
	TreeMap<String, FielderData> fmap;
	
	public FielderMap() {
		fmap = new TreeMap<>();
	}

	public void setFmap(TreeMap<String, FielderData> fmap) {
		this.fmap = fmap;
	}
	
	
	
}
