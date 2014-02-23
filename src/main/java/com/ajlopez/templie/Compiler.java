package com.ajlopez.templie;

import java.util.ArrayList;
import java.util.List;

class Compiler {
	private String text;
	private int length;
	private List<Step> steps = new ArrayList<Step>();
	private int from = 0;
	
	Compiler(String text) {
		this.text = text;
		this.length = text.length();
	}
	
	Template compile() {
		for (int k = 0; k < this.length - 1; k++) {
			if (this.text.charAt(k) != '$')
				continue;
			if (this.text.charAt(k + 1) != '{')
				continue;
			
			if (k > 0 && text.charAt(k - 1) == '\\') {
				if (k - 1> from) {
					this.steps.add(new StringStep(this.text.substring(this.from, k - 1)));
				}
				
				this.from = k;
				
				continue;
			}
			
			if (k > this.from) {
				this.steps.add(new StringStep(this.text.substring(this.from, k)));
			}
			
			this.from = k + 2;
			
			for (k++; k < this.length; k++)
				if (this.text.charAt(k) == '}')
					break;
			
			String name = this.text.substring(from, k).trim();
			
			this.steps.add(new VariableStep(name));
			
			this.from = k + 1; 
		}
		
		if (this.length > this.from)
			this.steps.add(new StringStep(this.text.substring(this.from, this.length)));
		
		return new Template(this.steps);		
	}
}
