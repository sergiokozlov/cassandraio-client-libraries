package io.cassandra.sdk.columnfamily;

import io.cassandra.sdk.StatusMessageModel;

import java.util.List;


@SuppressWarnings("serial")
public class ColumnFamilyModel extends StatusMessageModel {
	
	private ColumnFamily columnfamily;
	private List<Column> columns;

	public void setColumnfamily(ColumnFamily columnfamily) {
		this.columnfamily = columnfamily;
	}

	public ColumnFamily getColumnfamily() {
		return columnfamily;
	}
	
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	public List<Column> getColumns() {
		return columns;
	}
}
