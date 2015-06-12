package edu.sjsu.model;

public class VmData {

	private String instance_ID;
    private String instance_Name;
    private String instance_Taken;
    private String startTime;
    private String ram;
    private String diskSize;
    private String userId;
    private String instanceStatus;
    
    
    

	

	public String getInstanceStatus() {
		return instanceStatus;
	}

	public void setInstanceStatus(String instanceStatus) {
		this.instanceStatus = instanceStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public VmData(){}

    public String getInstance_ID() {
		return instance_ID;
	}

	public void setInstance_ID(String instance_ID) {
		this.instance_ID = instance_ID;
	}

	public String getInstance_Name() {
		return instance_Name;
	}

	public void setInstance_Name(String instance_Name) {
		this.instance_Name = instance_Name;
	}

	public String getInstance_Taken() {
		return instance_Taken;
	}

	public void setInstance_Taken(String instance_Taken) {
		this.instance_Taken = instance_Taken;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}    
		
	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getDiskSize() {
		return diskSize;
	}

	public void setDiskSize(String diskSize) {
		this.diskSize = diskSize;
	}

	public String toString() {
        return "VmData [instance_ID=" + instance_ID + ", instance_Name=" + instance_Name
                + ", instance_Taken=" + instance_Taken + ", startTime=" + startTime + "]";
    }  
}
