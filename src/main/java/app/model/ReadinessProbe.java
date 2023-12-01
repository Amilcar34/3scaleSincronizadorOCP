package app.model;

public class ReadinessProbe {

	public int failureThreshold;
	public HttpGet httpGet;
	public int initialDelaySeconds;
	public int periodSeconds;
	public int successThreshold;
	public int timeoutSeconds;

}
