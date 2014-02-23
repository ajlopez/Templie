package com.ajlopez.templie;

import java.util.Map;

class IfStep extends Step {
	private String name;
	private Template template;
	private boolean negated;
	
	IfStep(String name, Template template, boolean negated) {
		this.name = name;
		this.template = template;
		this.negated = negated;
	}
	
	@Override
	String run(Map<String, Object> model) {
		if (model.get(this.name) == null) {
			if (negated)
				return this.template.run(model);
		}
		else if (!negated)
			return this.template.run(model);
		
		return "";
	}
}
