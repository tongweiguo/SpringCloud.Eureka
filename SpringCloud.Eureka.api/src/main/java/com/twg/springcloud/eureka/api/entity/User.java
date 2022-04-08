package com.twg.springcloud.eureka.api.entity;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7377283932382988072L;

	private String id;
	
	private String localhost;
	
	private String serviceId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLocalhost() {
		return localhost;
	}

	public void setLocalhost(String localhost) {
		this.localhost = localhost;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public User(String id, String localhost, String serviceId) {
		super();
		this.id = id;
		this.localhost = localhost;
		this.serviceId = serviceId;
	}

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", localhost='" + localhost + '\'' +
				", serviceId='" + serviceId + '\'' +
				'}';
	}
}
