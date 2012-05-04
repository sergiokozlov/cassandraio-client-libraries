package io.cassandra.sdk;

import java.io.Serializable;

public class StatusMessageModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3981645705030785491L;
	private String message;
	private String status = "200";
	private String detail;
	private String error;
	private int ttl;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getTtl() {
		return ttl;
	}
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getError() {
		return error;
	}
	/**
	 * Returns true if the http status code is 200 or 201 
	 * otherwise returns false
	 * @return
	 */
	public boolean isOk() {
		if(getStatus().equals("200") ||
				getStatus().equals("201")){
			return true;
		}
		return false;
	}


	
}
