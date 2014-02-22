package com.ajlopez.templie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Template {
	private List<Step> steps;
	
	public Template(List<Step> steps) {
		this.steps = steps;
	}

	public static Template compile(String text) {
		List<Step> steps = new ArrayList<Step>();
		int l = text.length();
		int from = 0;
		
		for (int k = 0; k < l - 1; k++) {
			if (text.charAt(k) != '$')
				continue;
			if (text.charAt(k + 1) != '{')
				continue;
			
			if (k > from) {
				steps.add(new StringStep(text.substring(from, k)));
			}
			
			from = k + 2;
			
			for (k++; k < l; k++)
				if (text.charAt(k) == '}')
					break;
			
			String name = text.substring(from, k).trim();
			
			steps.add(new VariableStep(name));
			
			from = k + 1; 
		}
		
		if (l > from)
			steps.add(new StringStep(text.substring(from, l)));
		
		return new Template(steps);
	}
	
	public String run(Map<String, Object> model) {
		StringBuffer buffer = new StringBuffer();
		
		for (Step step: this.steps)
			buffer.append(step.run(model));
		
		return buffer.toString();
	}
}
