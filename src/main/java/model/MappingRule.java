package model;

import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(httpMethod, increment, metricMethodRef, pattern);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MappingRule other = (MappingRule) obj;
		return Objects.equals(httpMethod, other.httpMethod) && increment == other.increment
				&& Objects.equals(metricMethodRef, other.metricMethodRef) && Objects.equals(pattern, other.pattern);
	}

	@Override
	public String toString() {
		return "MappingRule [httpMethod=" + httpMethod + ", increment=" + increment + ", metricMethodRef="
				+ metricMethodRef + ", pattern=" + pattern + "]";
	}
	
	
}
