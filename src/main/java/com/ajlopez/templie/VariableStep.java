package com.ajlopez.templie;

import java.util.Map;

class VariableStep extends Step {
	private String name;
	
	VariableStep(String name) {
		this.name = name;
	}
	
	@Override
	String run(Map<java.lang.String, Object> model) {
		return model.get(this.name).toString();
	}
}
