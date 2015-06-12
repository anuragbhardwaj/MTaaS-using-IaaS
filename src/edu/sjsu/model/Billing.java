package edu.sjsu.model;

public class Billing {
	private int billId;
	private String userId;
	private String vmId;
	private Double billAmt;
	private String vmName;
		 
	
	public String getVmName() {
		return vmName;
	}


	public void setVmName(String vmName) {
		this.vmName = vmName;
	}


	public int getBillId() {
		return billId;
	}


	public void setBillId(int billId) {
		this.billId = billId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getVmId() {
		return vmId;
	}


	public void setVmId(String vmId) {
		this.vmId = vmId;
	}


	public Double getBillAmt() {
		return billAmt;
	}


	public void setBillAmt(Double billAmt) {
		this.billAmt = billAmt;
	}   
    
    public String toString() {
        return "Billing [billingid=" + billId + ", vmId = " + vmId + ", userid=" + userId + "billAmt = "+ billAmt + "]";
    }


	 
}
