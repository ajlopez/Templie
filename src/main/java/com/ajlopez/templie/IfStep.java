package com.ajlopez.templie;

import java.util.Map;

class IfStep extends Step {
	private String name;
	private Template template;
	
	IfStep(String name, Template template) {
		this.name = name;
		this.template = template;
	}
	
	@Override
	String run(Map<String, Object> model) {
		if (model.get(this.name) != null)
			return this.template.run(model);
		
		return "";
	}
}
