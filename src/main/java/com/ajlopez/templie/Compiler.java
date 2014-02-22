package com.ajlopez.templie;

import java.util.ArrayList;
import java.util.List;

class Compiler {
	String text;
	int length;
	
	Compiler(String text) {
		this.text = text;
		this.length = text.length();
	}
	
	Template compile() {
		List<Step> steps = new ArrayList<Step>();
		int from = 0;
		
		for (int k = 0; k < this.length - 1; k++) {
			if (text.charAt(k) != '$')
				continue;
			if (text.charAt(k + 1) != '{')
				continue;
			
			if (k > from) {
				steps.add(new StringStep(text.substring(from, k)));
			}
			
			from = k + 2;
			
			for (k++; k < this.length; k++)
				if (text.charAt(k) == '}')
					break;
			
			String name = text.substring(from, k).trim();
			
			steps.add(new VariableStep(name));
			
			from = k + 1; 
		}
		
		if (this.length > from)
			steps.add(new StringStep(text.substring(from, this.length)));
		
		return new Template(steps);		
	}
}
