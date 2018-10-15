package com.cg.obs.bean;

import java.sql.Date;

public class ServiceTracker {
	private int serviceId;
	private String serviceDescription;
	private Long accountId;
	private Date serviceRaisedDate;
	private String status;

	public int getService_id() {
		return serviceId;
	}

	public void setService_id(int service_id) {
		this.serviceId = service_id;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Date getServiceRaisedDate() {
		return serviceRaisedDate;
	}

	public void setServiceRaisedDate(Date serviceRaisedDate) {
		this.serviceRaisedDate = serviceRaisedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ServiceTracker() {
		super();
		
	}

	public ServiceTracker(int service_id, String serviceDescription,
			Long accountId, Date serviceRaisedDate, String status) {
		super();
		this.serviceId = service_id;
		this.serviceDescription = serviceDescription;
		this.accountId = accountId;
		this.serviceRaisedDate = serviceRaisedDate;
		this.status = status;
	}

	@Override
	public String toString() {
		String result = "\nService Request Number: " + this.serviceId
				+ "\nService Info: " + this.serviceDescription
				+ "\nService Raised on: " + this.serviceRaisedDate
				+ "\nService Status: " + this.status + "\n";
		return result;
	}

}
