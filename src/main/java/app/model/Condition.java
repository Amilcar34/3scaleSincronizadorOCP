package app.model;

import java.time.LocalDateTime;

public class Condition {

    public LocalDateTime lastTransitionTime;
    public Status status;
    public Type type;
	
    @Override
	public String toString() {
		return "Condition [lastTransitionTime=" + lastTransitionTime + ", status=" + status + ", type=" + type + "]";
	}
    
}