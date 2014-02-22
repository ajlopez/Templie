package com.ajlopez.templie;

import static org.junit.Assert.*;

import org.junit.Test;

public class TemplateTest {
	@Test
	public void compileSimpleString() {
		Template template = Template.compile("foo");
		assertNotNull(template);
	}

	@Test
	public void compileAndRunSimpleString() {
		Template template = Template.compile("foo");
		String result = template.run(null);		
		assertNotNull(result);
		assertEquals("foo", result);
	}
}
