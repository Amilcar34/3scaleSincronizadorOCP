package app.model;

import java.util.Map;

public class Spec {

	public MappingRule[] mappingRules;
    public String name;
    public String systemName;
    public Map<String, Map<String, String>> metrics;
    public String privateBaseURL;
    public Metadata providerAccountRef;

    public MappingRule[] getMappingRules() { return mappingRules; }
    public void setMappingRules(MappingRule[] value) { this.mappingRules = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getSystemName() { return systemName; }
    public void setSystemName(String value) { this.systemName = value; }

    public String getpublicBaseURL() { return privateBaseURL; }
    public void setpublicBaseURL(String value) { this.privateBaseURL = value; }

    public Metadata getProviderAccountRef() { return providerAccountRef; }
    public void setProviderAccountRef(Metadata value) { this.providerAccountRef = value; }
}
