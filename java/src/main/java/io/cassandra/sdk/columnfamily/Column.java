package io.cassandra.sdk.columnfamily;

import io.cassandra.sdk.StatusMessageModel;

@SuppressWarnings("serial")
public class Column extends StatusMessageModel {

	private boolean index;
	private String validator;
	private String name;
	
	public boolean isIndex() {
		return index;
	}
	public void setIndex(boolean index) {
		this.index = index;
	}
	public String getValidator() {
		return validator;
	}
	public void setValidator(String validator) {
		this.validator = validator;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
