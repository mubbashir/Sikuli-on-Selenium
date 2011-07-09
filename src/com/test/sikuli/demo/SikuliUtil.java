package com.test.sikuli.demo;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

public class SikuliUtil {
	public static boolean screenWaitAndClick(Screen screen, String regionImagePath, int timeOut){
		boolean result = false;
		
		
		try {
			screen.exists(regionImagePath, timeOut);
			screen.click(regionImagePath,0);
			result =true;
		} catch (FindFailed e) {
			result = false;	
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	public static boolean screenPageDownUntillVisible(Screen screen, String region, int pageDownCountOut) {
		boolean result = false;
		try {
			for (int second = 0;; second++) {
				if (second > pageDownCountOut) {
					result = false;
					break;
				}
				try {
					if (screen.exists(region)!= null) {
						result = true;
						break;
					}
				} catch (Exception e) {
				}
				screen.type(null, '\ue005'+"", 0);
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	
}
