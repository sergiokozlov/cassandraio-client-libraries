package io.cassandra.sdk.constants;

import java.util.ResourceBundle;

public class APIConstants {

	private static ResourceBundle bundle = ResourceBundle.getBundle("sdk");
	public static String API_URL = bundle.getString("apiUrl");
	public static String API_VERSION = bundle.getString("version");
	
	public static String METH_GET = "GET";
	public static String METH_POST = "POST";
	public static String METH_DELETE = "DELETE";
	
	public static String COMPARATOR_UTF8 = "UTF8Type";
	public static String COMPARATOR_LONG = "LongType";
	public static String COMPARATOR_UUID = "UUIDType";
	public static String COMPARATOR_TIME = "TimeUUIDType";
	public static String COMPARATOR_INT = "IntegerType";
	public static String COMPARATOR_ASCII = "ASCIIType";
	public static String COMPARATOR_COUNTER = "CounterType";
	
	public static String API_COLUMN = "column";
	public static String API_COLUMNFAMILY = "columnfamily";
	public static String API_DATA = "data";
	public static String API_CQL = "cql";
	public static String API_KEYSPACE = "keyspace";
	public static String API_COUNTER = "counter";
}
