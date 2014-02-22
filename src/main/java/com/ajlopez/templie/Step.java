package com.ajlopez.templie;

import java.util.Map;

abstract class Step {
	abstract String run(Map<String, Object> model);
}
