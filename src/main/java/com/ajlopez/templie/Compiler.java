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
			if (!this.isExpression(k))
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
	
	private boolean isExpression(int position) {
		char ch = this.text.charAt(position);
		
		if (ch != '$')
			return false;
		
		if (position + 1 >= this.length)
			return false;
		
		if (text.charAt(position + 1) != '{')
			return false;
		
		if (position > 0 && text.charAt(position - 1) == '\\') {
			if (position - 1> from) {
				this.steps.add(new StringStep(this.text.substring(this.from, position - 1)));
			}
			
			this.from = position;
			
			return false;
		}
		
		return true;
	}
}
