package model;

public class MappingRule {

	public String httpMethod;
	public long increment;
	public String metricMethodRef;
	public String pattern;

	public String getHTTPMethod() {
		return httpMethod;
	}

	public void setHTTPMethod(String value) {
		this.httpMethod = value;
	}

	public long getIncrement() {
		return increment;
	}

	public void setIncrement(long value) {
		this.increment = value;
	}

	public String getMetricMethodRef() {
		return metricMethodRef;
	}

	public void setMetricMethodRef(String value) {
		this.metricMethodRef = value;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String value) {
		this.pattern = value;
	}
}
