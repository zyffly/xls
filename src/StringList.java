

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class StringList {

	public List<LinkedHashMap> mList;
	public LinkedHashMap<Integer,String> titleMap;

	public StringList() {
		mList = new ArrayList<>();
	}

	public void setTitelMap(LinkedHashMap<Integer,String> map) {
		this.titleMap = map;
	}

	public List<LinkedHashMap> getList() {
		return mList;
	}

	public List<String> outData(List<LinkedHashMap> list) {
		List mStringlist = new ArrayList<>();
		if (titleMap != null) {
//			list.add(0, titleMap);
			for (LinkedHashMap mMap : list) {
				String s = "";
				for(int i = 0 ; i < titleMap.size() ; i ++){
					if(mMap.containsKey(i)){
						s = s + mMap.get(i) + ",";
					} else {
						s = s + "" + ",";
					}
				}
				s = s + "\n";
				mStringlist.add(s);
			}
		}
		return mStringlist;
	}
}
