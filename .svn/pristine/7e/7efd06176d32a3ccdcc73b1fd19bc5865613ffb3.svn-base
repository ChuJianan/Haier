package com.haier.utils;

import java.util.Comparator;

import com.haier.bean.Glory;

public class ComparatorValues implements Comparator{

	

	@Override
	public int compare(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		Glory aGlory=(Glory)arg0;
		Glory bGlory=(Glory)arg1;
		if (aGlory.getRanking() < bGlory.getRanking())  
            return -1;  
        else if (aGlory.getRanking() == bGlory.getRanking())  
            return 0;  
        else if (aGlory.getRanking() > bGlory.getRanking())  
            return 1;  
        return 0;  
	}

}
