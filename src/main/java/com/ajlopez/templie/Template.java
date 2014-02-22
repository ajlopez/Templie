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
		steps.add(new StringStep(text));
		
		return new Template(steps);
	}
	
	public String run(Map<String, Object> model) {
		StringBuffer buffer = new StringBuffer();
		
		for (Step step: this.steps)
			buffer.append(step.run(model));
		
		return buffer.toString();
	}
}
