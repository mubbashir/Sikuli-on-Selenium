package com.test.sikuli.demo;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.annotations.BeforeSuite;

public class SikuliUtil {
	
	public static Screen screen;
	
	@BeforeSuite
	public void initScreen() {
		screen = new Screen();
	}
	public static boolean screenWaitAndClick( String regionImagePath, int timeOut){
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
	
	public static boolean screenPageDownUntillVisible( String region, int pageDownCountOut) {
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
