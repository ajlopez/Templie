package com.ajlopez.templie;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TemplateTest {
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
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

	@Test
	public void compileIfWithSpaces() throws CompileException {
		Template template = Template.compile("   @if    name\r\n${name}\r\n   @end");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", "Adam");
		
		String result = template.run(model);
		
		assertNotNull(result);
		assertEquals("Adam\r\n", result);
	}

	@Test
	public void compileIfWithFalseCondition() throws CompileException {
	    Template template = Template.compile("@if name\r\n${name}\r\n@end");
		Map<String, Object> model = new HashMap<String, Object>();
		
		String result = template.run(model);
		
		assertNotNull(result);
		assertEquals("", result);
	}

	@Test
	public void compileIfWithNegatedCondition() throws CompileException {
	    Template template = Template.compile("@if not name\r\nHello\r\n@end");
		Map<String, Object> model = new HashMap<String, Object>();
		
		String result = template.run(model);
		
		assertNotNull(result);
		assertEquals("Hello\r\n", result);
	}

	@Test
	public void raiseIfNoMissingEnd() throws CompileException {
		expectedEx.expect(CompileException.class);
	    expectedEx.expectMessage("Missing end command");
	    
		Template template = Template.compile("@if name\r\n${name}\r\n");
		Map<String, Object> model = new HashMap<String, Object>();
		
		template.run(model);		
	}

	@Test
	public void raiseIfInvalidCommand() throws CompileException {
		expectedEx.expect(CompileException.class);
	    expectedEx.expectMessage("Unknown command 'of'");
	    
		Template template = Template.compile("@of name\r\n${name}\r\n@end");
		Map<String, Object> model = new HashMap<String, Object>();
		
		template.run(model);		
	}

	@Test
	public void raiseIfInvalidIfCommandWithAdditionalArgument() throws CompileException {
		expectedEx.expect(CompileException.class);
	    expectedEx.expectMessage("Invalid if command");
	    
		Template template = Template.compile("@if name1 name2\r\n${name}\r\n@end");
		Map<String, Object> model = new HashMap<String, Object>();
		
		template.run(model);		
	}

	@Test
	public void raiseIfInvalidIfCommandWithNoArgument() throws CompileException {
		expectedEx.expect(CompileException.class);
	    expectedEx.expectMessage("Invalid if command");
	    
		Template template = Template.compile("@if\r\n${name}\r\n@end");
		Map<String, Object> model = new HashMap<String, Object>();
		
		template.run(model);		
	}
}
