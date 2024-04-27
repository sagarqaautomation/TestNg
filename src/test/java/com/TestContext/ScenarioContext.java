package com.TestContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScenarioContext {

	private HashMap<String, Object> testData;
	private HashMap<String, List<String>> listTestData;

	public ScenarioContext() {
		testData = new HashMap<>();
		listTestData = new HashMap<>();
	}

	public void addTestData(String key, String Value) {

		testData.put(key, Value);
	}

	public void addTestData(String key, List<String> Value) {
		listTestData.put(key, Value);
	}


	public String getTestData(String key) {
		return testData.get(key).toString();
	}

	public List<String> getListTestData(String key) {
		return listTestData.get(key);
	}
}
