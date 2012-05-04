package io.cassandra.sdk.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DataColumn implements Serializable {
	
	private String columnname;
	private String columnvalue;
	private int ttl;
	
	public DataColumn(String columnname, String columnvalue, int ttl){
		this.columnname = columnname;
		this.columnvalue = columnvalue;
		this.ttl = ttl;
	}
	
	public DataColumn(String columnname, String columnvalue){
		this.columnname = columnname;
		this.columnvalue = columnvalue;
	}
	
	public String getColumnname() {
		return columnname;
	}
	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}
	public String getColumnvalue() {
		return columnvalue;
	}
	public void setColumnvalue(String columnvalue) {
		this.columnvalue = columnvalue;
	}
	public int getTtl() {
		return ttl;
	}
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	
	

}
