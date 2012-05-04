package io.cassandra.sdk.columnfamily;

import io.cassandra.sdk.StatusMessageModel;

import java.util.List;


@SuppressWarnings("serial")
public class ColumnFamiliesModel extends StatusMessageModel {
	
	private List<ColumnFamilyModel> columnfamilies;

	public void setColumnfamilies(List<ColumnFamilyModel> columnfamilies) {
		this.columnfamilies = columnfamilies;
	}

	public List<ColumnFamilyModel> getColumnfamilies() {
		return columnfamilies;
	}

}
