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
		
		while (true) {
			int position = text.indexOf("${");
			
			if (position < 0) {
				steps.add(new StringStep(text));
				break;
			}
			
			steps.add(new StringStep(text.substring(0, position)));
			
			int position2 = text.indexOf("}", position);
			
			String name = text.substring(position + 2, position2);
			
			steps.add(new VariableStep(name));
			
			text = text.substring(position2 + 1);
		}
		
		return new Template(steps);
	}
	
	public String run(Map<String, Object> model) {
		StringBuffer buffer = new StringBuffer();
		
		for (Step step: this.steps)
			buffer.append(step.run(model));
		
		return buffer.toString();
	}
}
