package com.ajlopez.templie;

import java.util.Map;

class StringStep extends Step {
	private String text;
	
	StringStep(String text) {
		this.text = text;
	}
	
	@Override
	String run(Map<java.lang.String, Object> model) {
		return this.text;
	}
}
