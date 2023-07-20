package baseballRecord;

import java.util.TreeMap;

public class FielderMap {

	String teamAndUniNum;
	
	TreeMap<String, FielderData> fmap;
	
	public FielderMap() {
		fmap = new TreeMap<>();
	}

	public void setFmap(TreeMap<String, FielderData> fmap) {
		this.fmap = fmap;
	}
	
	
	
}
