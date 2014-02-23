package com.ajlopez.templie;

import java.util.Map;

class TemplateStep extends Step {
	private Template template;
	
	TemplateStep(Template template) {
		this.template = template;
	}
	
	@Override
	String run(Map<String, Object> model) {
		return this.template.run(model);
	}
}
