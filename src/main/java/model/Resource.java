package model;

public class Resource {

	public String cpu;
	public String memory;
		
	public Resource(String cpu, String memory) {
		super();
		this.cpu = cpu;
		this.memory = memory;
	}
	
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	
	@Override
	public String toString() {
		return "Resource [cpu=" + cpu + ", memory=" + memory + "]";
	}
}
