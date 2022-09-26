package com.ningyou.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TimeFormatter {
	
	private static Map<Integer, String> timeUnit = new HashMap<>();		
	
	public static String formatDuration(int seconds) {
		
		timeUnit.put(1, "second");
		timeUnit.put(60, "minute");
		timeUnit.put(3600, "hour");
		timeUnit.put(86400, "day");
		timeUnit.put(31536000, "year");
		timeUnit.put(Integer.MAX_VALUE, "null");
		
		if(seconds == 0) {
			return "0 Seconds";
		}
		
		List<Integer> keys = timeUnit.keySet().stream().sorted().collect(Collectors.toList());
		
		for(int i = 0; i < keys.size(); i++) {
			if(seconds < keys.get(i) || (i+1 > keys.size())) {
				
				int unit = 0;
				int unitMagnitude = keys.get(i-1);

				unit = (seconds / unitMagnitude);
				seconds = seconds - (unitMagnitude * unit);
				
				String formatter = unit + " " + timeUnit.get(keys.get(i-1));
				
				if(unit > 1) {
					formatter += "s";
				}
				
				if(seconds == 0) {
					return formatter;
				}
				
				
				int amount = 0;
				int s2 = seconds;
				for(int k = i-(2); k >= 0; k--) {
					if(s2 >= keys.get(k)) {
						s2 = s2 - (keys.get(k) * (s2 / keys.get(k)));
						amount++;
					}
				}
				
				if(amount == 1) {
					return formatter + " and " + formatDuration(seconds);
				}
				
				return formatter + ", " + formatDuration(seconds);
			}
			
		}
		
		return "";
	}
	
	public static void main(String[] args) {
		System.out.println(TimeFormatter.formatDuration(132030004));
		
		//132030004
		//7664431
		
		//int years = 4;
		//int days = 68;
		//int hours = 3;
		//int seconds = 4;
		
		//System.out.println(seconds + hours*3600 + days*86400 + years*31536000);
		
	}
	
	
}
