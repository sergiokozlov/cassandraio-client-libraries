# java

A Java helper library for a RESTful interface with a CassandraIO application.

## Prerequisites

* Java

* Maven

## Usage

### Creating a keyspace:

	public StatusMessageModel createKeyspaceTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());
		
		KeyspaceAPI api = new KeyspaceAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);
		
		StatusMessageModel sm = api.createKeyspace(KS);
		System.out.println(sm.getMessage() +" | "+ sm.getDetail() +" | "+ sm.getError());
		
		return sm;
	}

### Creating a column family

	public StatusMessageModel addColumnFamilyTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		ColumnFamilyAPI api = new ColumnFamilyAPI(APIConstants.API_URL, TOKEN,
				ACCOUNTID);

		StatusMessageModel sm = api.createColumnFamily(KS, CF,
				APIConstants.COMPARATOR_UTF8);

		System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
				+ sm.getError());
		return sm;
	}

### Creating a counter column family

	public StatusMessageModel addCounterColumnFamilyTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		ColumnFamilyAPI api = new ColumnFamilyAPI(APIConstants.API_URL, TOKEN,
				ACCOUNTID);

		StatusMessageModel sm = api.createColumnFamily(KS, CF_CNT,
				APIConstants.COMPARATOR_COUNTER);

		System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
				+ sm.getError());
		return sm;
	}

### Getting information about a column family

	public StatusMessageModel getColumnFamilyTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		ColumnFamilyAPI api = new ColumnFamilyAPI(APIConstants.API_URL, TOKEN,
				ACCOUNTID);

		ColumnFamilyModel cm = api.getColumnFamily(KS, CF);
		System.out.println(cm.getColumnfamily().getName() + " |"
				+ cm.getColumnfamily().getSortedby());
		for (Column col : cm.getColumns()) {
			System.out.println(col.isIndex() + " | " + col.getValidator()
					+ " | " + col.getName());
		}

		return cm;

	}

### Getting information about all my column families in a keyspace

	public StatusMessageModel getColumnFamiliesTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		ColumnFamilyAPI api = new ColumnFamilyAPI(APIConstants.API_URL, TOKEN,
				ACCOUNTID);

		ColumnFamiliesModel fams = api.getColumnFamilies(KS);
		StatusMessageModel sm = null;
		for (ColumnFamilyModel cm : fams.getColumnfamilies()) {
			System.out.println(cm.getColumnfamily().getName() + " |"
					+ cm.getColumnfamily().getSortedby());
			for (Column col : cm.getColumns()) {
				System.out.println(col.isIndex() + " | " + col.getValidator()
						+ " | " + col.getName());
			}
			sm = cm;
		}

		return sm;

	}

### Deleting a column family

	public StatusMessageModel deleteColumnFamilyTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		ColumnFamilyAPI api = new ColumnFamilyAPI(APIConstants.API_URL, TOKEN,
				ACCOUNTID);

		StatusMessageModel sm = api.deleteColumnFamily(KS, CF);

		System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
				+ sm.getError());
		return sm;
	}

### Creating a column

	public StatusMessageModel addColumnTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		ColumnAPI api = new ColumnAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);

		StatusMessageModel sm = api.upsertColumn(KS, CF, COL,
				APIConstants.COMPARATOR_UTF8, true);

		System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
				+ sm.getError());
		return sm;
	}

### Posting data

	public StatusMessageModel addDataTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		DataAPI api = new DataAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);

		Map<String, String> params = new HashMap<String, String>();
		params.put("first key", "first value");
		params.put("second_key", "second value");
		params.put("SDKTestColumn", "columnIndex");

		StatusMessageModel sm = api.postData(KS, CF, RK, params, 10000);
		System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
				+ sm.getError());

		return sm;
	}

### Posting bulk data

	public StatusMessageModel addDataBulkTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		DataAPI api = new DataAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);

		List<DataColumn> columns = new ArrayList<DataColumn>();
		DataColumn dc = new DataColumn("bulkcolumn1", "bulkvalue");
		columns.add(dc);
		dc = new DataColumn("SDKTestColumn", "bulk secodary index", 12000);
		columns.add(dc);

		List<DataRowkey> rows = new ArrayList<DataRowkey>();
		DataRowkey row = new DataRowkey("rowKey1", columns);
		rows.add(row);
		row = new DataRowkey("rowKey2", columns);
		rows.add(row);

		DataBulkModel dataBulk = new DataBulkModel(rows);

		StatusMessageModel sm = api.postBulkData(KS, CF, dataBulk);
		System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
				+ sm.getError());

		return sm;
	}

### Incrementing a counter

	public StatusMessageModel incrementCounterTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		CounterAPI api = new CounterAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);
		StatusMessageModel sm = api.incrementCounter(KS, CF_CNT, RK, COL, 1);
		
		DataMapModel map = api.getCounter(KS, CF_CNT, RK, COL);
		System.out.println("Counter Result: "+ map.toString());
		
		return sm;
		
	}

### Deleting data

	public StatusMessageModel deleteDataColumnTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		DataAPI api = new DataAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);
		StatusMessageModel sm = api.deleteData(KS, CF, RK);

		System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
				+ sm.getError());
		return sm;
	}

### Getting data

	public void getDataTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		DataAPI api = new DataAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);
		DataMapModel sm = api.getData(KS, CF, RK, 0, null);

		System.out.println(sm.toString());
	}

### Deleting a keyspace

	public void deleteKeyspaceTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		KeyspaceAPI api = new KeyspaceAPI(APIConstants.API_URL, TOKEN,
				ACCOUNTID);

		StatusMessageModel sm = api.deleteKeyspace(KS);
		System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
				+ sm.getError());

	}
