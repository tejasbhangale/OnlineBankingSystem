package com.cg.obs.bean;

import java.sql.Date;

public class ServiceTracker {
	private int serviceId;
	private String serviceDescription;
	private int accountId;
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

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
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
		// TODO Auto-generated constructor stub
	}

	public ServiceTracker(int service_id, String serviceDescription,
			int accountId, Date serviceRaisedDate, String status) {
		super();
		this.serviceId = service_id;
		this.serviceDescription = serviceDescription;
		this.accountId = accountId;
		this.serviceRaisedDate = serviceRaisedDate;
		this.status = status;
	}

	@Override
	public String toString() {
<<<<<<< HEAD
		String result = "\nService Request Number: " + this.service_id
=======
		String result = "Service Request Number: " + this.serviceId
>>>>>>> 99d7e53eaba8390e835cab0c37d5e7f8234b66b7
				+ "\nService Info: " + this.serviceDescription
				+ "\nService Raised on: " + this.serviceRaisedDate
				+ "\nService Status: " + this.status + "\n";
		return result;
	}

}
