package io.cassandra.tests;

import static org.junit.Assert.assertTrue;
import io.cassandra.sdk.StatusMessageModel;
import io.cassandra.sdk.column.ColumnAPI;
import io.cassandra.sdk.columnfamily.Column;
import io.cassandra.sdk.columnfamily.ColumnFamiliesModel;
import io.cassandra.sdk.columnfamily.ColumnFamilyAPI;
import io.cassandra.sdk.columnfamily.ColumnFamilyModel;
import io.cassandra.sdk.constants.APIConstants;
import io.cassandra.sdk.counter.CounterAPI;
import io.cassandra.sdk.cql.CqlAPI;
import io.cassandra.sdk.cql.CqlMapModel;
import io.cassandra.sdk.data.DataAPI;
import io.cassandra.sdk.data.DataBulkModel;
import io.cassandra.sdk.data.DataColumn;
import io.cassandra.sdk.data.DataMapModel;
import io.cassandra.sdk.data.DataRowkey;
import io.cassandra.sdk.keyspace.KeyspaceAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class SDKTests {

	// test credentials
	private static String TOKEN = "lCR72YxFPq";
	private static String ACCOUNTID = "25c5887e-b8e8-46ba-b025-6083415e5039";
	// private static String TOKEN = "i8piR57r8W";
	// private static String ACCOUNTID = "a57e3a41-ad44-42fa-a47a-ceb2e6f83699";

	private static String KS = "SDKTestKS";
	private static String CF = "SDKTestCF";
	private static String CF_CNT = "SDKTestCntCF";
	private static String COL = "SDKTestCOL";
	private static String RK = "SDKTestRK";

	@Test
	public void runAllTests() {
		
		try {
			deleteKeyspaceTest();
		} catch (Exception e) {
		}
		
		StatusMessageModel sm = null;
		try {
			sm = createKeyspaceTest();
			assertTrue(sm.isOk());
		} catch (Exception e) {
		}

		try {
			sm = addColumnFamilyTest();
			assertTrue(sm.isOk());
		} catch (Exception e) {
		}
		
		try {
			sm = addCounterColumnFamilyTest();
			assertTrue(sm.isOk());
		} catch (Exception e) {
		}


		try {
			sm = getColumnFamilyTest();
			assertTrue(sm.isOk());
		} catch (Exception e) {
		}

		try {
			sm = getColumnFamiliesTest();
			assertTrue(sm.isOk());
		} catch (Exception e) {
		}

		try {
			sm = addColumnTest();
			assertTrue(sm.isOk());
		} catch (Exception e) {
		}

		try {
			sm = addDataTest();
			assertTrue(sm.isOk());
		} catch (Exception e) {
		}

		try {
			sm = incrementCounterTest();
			assertTrue(sm.isOk());
		} catch (Exception e) {
		}
		
		try {
			sm = addDataBulkTest();
			//assertTrue(sm.isOk());
		} catch (Exception e) {
		}

		try {
			getDataTest();
		} catch (Exception e) {
		}

		try {
			cqlTest();
			assertTrue(sm.isOk());
		} catch (Exception e) {
		}

		try {
			sm = deleteDataColumnTest();
			assertTrue(sm.isOk());
		} catch (Exception e) {
		}

		try {
			sm = deleteColumnFamilyTest();
			assertTrue(sm.isOk());
		} catch (Exception e) {
		}

		try {
			deleteKeyspaceTest();
		} catch (Exception e) {
		}
	}

	public StatusMessageModel createKeyspaceTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());
		
		KeyspaceAPI api = new KeyspaceAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);
		
		StatusMessageModel sm = api.createKeyspace(KS);
		System.out.println(sm.getMessage() +" | "+ sm.getDetail() +" | "+ sm.getError());
		
		return sm;
	}

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

	public StatusMessageModel deleteColumnFamilyTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		ColumnFamilyAPI api = new ColumnFamilyAPI(APIConstants.API_URL, TOKEN,
				ACCOUNTID);

		StatusMessageModel sm = api.deleteColumnFamily(KS, CF);

		System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
				+ sm.getError());
		return sm;
	}

	public StatusMessageModel addColumnTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		ColumnAPI api = new ColumnAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);

		StatusMessageModel sm = api.upsertColumn(KS, CF, COL,
				APIConstants.COMPARATOR_UTF8, true);

		System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
				+ sm.getError());
		return sm;
	}

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
	
	public StatusMessageModel incrementCounterTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		CounterAPI api = new CounterAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);
		StatusMessageModel sm = api.incrementCounter(KS, CF_CNT, RK, COL, 1);
		
		DataMapModel map = api.getCounter(KS, CF_CNT, RK, COL);
		System.out.println("Counter Result: "+ map.toString());
		
		return sm;
		
	}

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

	public StatusMessageModel deleteDataColumnTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		DataAPI api = new DataAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);
		StatusMessageModel sm = api.deleteData(KS, CF, RK);

		System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
				+ sm.getError());
		return sm;
	}

	public void getDataTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		DataAPI api = new DataAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);
		DataMapModel sm = api.getData(KS, CF, RK, 0, null);

		System.out.println(sm.toString());
	}

	/*
	 * public void counterTest() throws Exception { CounterAPI api = new
	 * CounterAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);
	 * 
	 * StatusMessageModel sm = api.decrementCounter("SDKTestRow", "SDKCounter",
	 * 1);
	 * 
	 * System.out.println(sm.getMessage() +" | "+ sm.getDetail() +" | "+
	 * sm.getError()); assertTrue(sm.isOk());
	 * 
	 * }
	 * 
	 * public void getCounterTest() throws Exception { CounterAPI api = new
	 * CounterAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);
	 * 
	 * DataMapModel m = api.getCounter("SDKTestRow", "SDKCounter");
	 * 
	 * System.out.println(m.toString()); }
	 */

	public StatusMessageModel cqlTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		CqlAPI api = new CqlAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);

		CqlMapModel sm = api
				.executeCqlQuery(
						KS,
						"calls",
						"SELECT 'CallSid', 'Caller' WHERE "
								+ "'AccountSid' = 'ACee3d4f069610816247170d8b47f967c0'",
						true);
		if (sm.isOk()) {
			System.out.println(sm.getCqlMap().toString());
		} else {
			System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
					+ sm.getError());
		}
		return sm;
	}

	public void deleteKeyspaceTest() throws Exception {
		System.out.println(this.getClass().getName()+"."+ new Exception().getStackTrace()[0].getMethodName());

		KeyspaceAPI api = new KeyspaceAPI(APIConstants.API_URL, TOKEN,
				ACCOUNTID);

		StatusMessageModel sm = api.deleteKeyspace(KS);
		System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
				+ sm.getError());

	}
}
