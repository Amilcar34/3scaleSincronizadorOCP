package app.model;

public class Backend {
	
	public String apiVersion;
	public String kind;
	public Metadata metadata;
	public Spec spec;

	public String getAPIVersion() {
		return apiVersion;
	}

	public void setAPIVersion(String value) {
		this.apiVersion = value;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String value) {
		this.kind = value;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata value) {
		this.metadata = value;
	}

	public Spec getSpec() {
		return spec;
	}

	public void setSpec(Spec value) {
		this.spec = value;
	}
}
