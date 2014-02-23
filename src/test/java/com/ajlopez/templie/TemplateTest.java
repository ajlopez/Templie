package com.ajlopez.templie;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TemplateTest {
	@Test
	public void compileSimpleString() throws CompileException {
		Template template = Template.compile("foo");
		
		assertNotNull(template);
	}

	@Test
	public void compileAndRunSimpleString() throws CompileException {
		Template template = Template.compile("foo");
		
		String result = template.run(null);
		
		assertNotNull(result);
		assertEquals("foo", result);
	}

	@Test
	public void compileAndRunWithOneVariable() throws CompileException {
		Template template = Template.compile("Hello, ${name}!");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", "World");
		
		String result = template.run(model);
		
		assertNotNull(result);
		assertEquals("Hello, World!", result);
	}

	@Test
	public void compileAndRunWithOneEscapedVariable() throws CompileException {
		Template template = Template.compile("Hello, \\${name}!");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", "World");
		
		String result = template.run(model);
		
		assertNotNull(result);
		assertEquals("Hello, ${name}!", result);
	}

	@Test
	public void compileAndRunWithTwoVariables() throws CompileException {
		Template template = Template.compile("${hello}, ${name}!");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", "World");
		model.put("hello", "Hi");
		
		String result = template.run(model);
		
		assertNotNull(result);
		assertEquals("Hi, World!", result);
	}

	@Test
	public void compileAndRunWithTwoVariablesWithSpaces() throws CompileException {
		Template template = Template.compile("${  hello  }, ${  name }!");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", "World");
		model.put("hello", "Hi");
		
		String result = template.run(model);
		
		assertNotNull(result);
		assertEquals("Hi, World!", result);
	}

	@Test
	public void compileIfWithTrueCondition() throws CompileException {
		Template template = Template.compile("@if name\r\n${name}\r\n@end");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", "Adam");
		
		String result = template.run(model);
		
		assertNotNull(result);
		assertEquals("Adam\r\n", result);
	}
}
