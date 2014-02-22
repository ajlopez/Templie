package com.ajlopez.templie;

public class Template {
	private String text;
	
	public Template(String text) {
		this.text = text;
	}

	public static Template compile(String text) {
		return new Template(text);
	}
	
	public String run(Object object) {
		// TODO Auto-generated method stub
		return this.text;
	}

}
