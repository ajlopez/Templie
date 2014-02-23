package com.ajlopez.templie;

import java.util.ArrayList;
import java.util.List;

class Compiler {
	private String text;
	private int length;
	private int from = 0;
	
	Compiler(String text) {
		this.text = text;
		this.length = text.length();
	}
	
	Template compile() throws CompileException {
		List<Step> steps = new ArrayList<Step>();
		
		return this.compile(0, steps, false);
	}

	private Template compile(int position, List<Step> steps, boolean useend) throws CompileException {
		for (int k = position; k < this.length - 1; k++) {
			if (this.isExpression(k, steps)) {
				this.processExpression(k, steps);
				k = this.from - 1;
				continue;
			}
			
			if (this.isCommand(k, steps)) {
				boolean isend = this.processCommand(k, steps);
				
				if (isend && useend)
					return new Template(steps);
				
				k = this.from - 1;
				
				continue;
			}
		}
		
		if (useend)
			throw new CompileException("Missing end command");
		
		if (this.length > this.from)
			steps.add(new StringStep(this.text.substring(this.from, this.length)));
		
		return new Template(steps);		
	}
	
	private void processExpression(int position, List<Step> steps) {
		if (position > this.from)
			steps.add(new StringStep(this.text.substring(this.from, position)));
		
		this.from = position + 2;
		
		for (position++; position < this.length; position++)
			if (this.text.charAt(position) == '}')
				break;
		
		String name = this.text.substring(from, position).trim();
		
		steps.add(new VariableStep(name));
		
		this.from = position + 1; 		
	}
	
	private boolean processCommand(int position, List<Step> steps) throws CompileException {
		int k;
		char ch = 0;
		
		for (k = position + 1; k < this.length; k++) {
			ch = this.text.charAt(k);
			
			if (ch == '\r' || ch == '\n')
				break;
		}
		
		String cmdtext = text.substring(position + 1, k);
		
		if (ch == 0)
			this.from = k;
		else if (ch == '\n')
			this.from = k + 1;
		else if (k + 1 < length && this.text.charAt(k + 1) == '\n')
			this.from = k + 2;
		else
			this.from = k + 1;
		
		String[] words = cmdtext.split("\\s+");
		
		if (words[0].equals("end"))
			return true;
		
		if (!words[0].equals("if"))
			throw new CompileException("Invalid command");
		
		Template template = this.compile(this.from, new ArrayList<Step>(), true);
		
		steps.add(new IfStep(words[1], template));
		
		return false;
	}
	
	private boolean isCommand(int position, List<Step> steps) {
		char ch = this.text.charAt(position);
		
		if (ch != '@')
			return false;
		
		if (position + 1 >= this.length)
			return false;
		
		if (!Character.isLetter(this.text.charAt(position + 1)))
			return false;
		
		int k;
		
		for (k = position - 1; k >= 0; k--) {
			char ach = this.text.charAt(k);
			
			if (ach == '\r' || ach == '\n')
				break;
			
			if (Character.isWhitespace(ach))
				continue;
			
			return false;
		}
		
		k++;
		
		if (this.from < k)
			steps.add(new StringStep(this.text.substring(this.from, k)));
		
		return true;
	}
	
	private boolean isExpression(int position, List<Step> steps) {
		char ch = this.text.charAt(position);
		
		if (ch != '$')
			return false;
		
		if (position + 1 >= this.length)
			return false;
		
		if (text.charAt(position + 1) != '{')
			return false;
		
		if (position > 0 && text.charAt(position - 1) == '\\') {
			if (position - 1 > from)
				steps.add(new StringStep(this.text.substring(this.from, position - 1)));
			
			this.from = position;
			
			return false;
		}
		
		return true;
	}
}
