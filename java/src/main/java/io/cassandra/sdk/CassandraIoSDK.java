package io.cassandra.sdk;

import io.cassandra.sdk.conn.APIConnection;
import io.cassandra.sdk.conn.DefaultAPIConnection;
import io.cassandra.sdk.constants.APIConstants;
import io.cassandra.sdk.exception.CassandraIoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class CassandraIoSDK {
	protected Logger log = Logger.getLogger(CassandraIoSDK.class.getName());
	
	public static String METH_GET = APIConstants.METH_GET;
	public static String METH_POST = APIConstants.METH_POST;
	public static String METH_DELETE = APIConstants.METH_DELETE;
	public static String ENCODING = "UTF-8";
	
	private String method;
	private String apiUrl;
	private String params;
	private String token;
	private String accountId;
	private APIConnection apiConnection;
	
	/**
	 * Initialize's the API without Basic Authentication
	 * @param apiUrl
	 * @param accountId
	 * @param method
	 */
	private CassandraIoSDK(APIConnection apiConnection, String apiUrl){
		this.apiConnection = apiConnection;
		this.apiUrl = apiUrl;
	}
	
	/**
	 * Initializes the API with Basic Authentication uses StandardAPIConnection by default
	 * @param apiUrl
	 * @param method
	 * @param token
	 * @param accountId
	 */
	public CassandraIoSDK(String apiUrl, String token, String accountId){
		this(new DefaultAPIConnection(), apiUrl);
		this.token = token;
		this.accountId = accountId;
	}
	
	/**
	 * Initializes the API with Basic Authentication
	 * @param apiUrl
	 * @param method
	 * @param token
	 * @param accountId
	 */
	public CassandraIoSDK(APIConnection apiConnection, String apiUrl, String token, String accountId){
		this(apiConnection, apiUrl);
		this.token = token;
		this.accountId = accountId;
	}
	
	/**
	 * 
	 * @param apiType
	 * @param id
	 * @param method
	 * @return
	 * @throws CassandraIoException
	 */
	protected JsonElement getJsonResponse(String apiType, String id, String method) throws CassandraIoException{
		setMethod(method);
		return getJsonResponse(apiType, id, method, null);
	}
	
	/**
	 * 
	 * @param apiType
	 * @param id
	 * @param method
	 * @param params
	 * @return
	 * @throws CassandraIoException
	 */
	protected JsonElement getJsonResponse(String apiType, String id, String method, String params) throws CassandraIoException{

		setMethod(method);
		setParams(params);
		try {
			
			return getJsonResponse(constructAPIUrl(apiType, id));
			
		} catch (Exception e) {
			log.log(Level.SEVERE, "Error in request", e);
			throw new CassandraIoException("CloudsandraException getJsonElement", e);
		} 
	}
	
	
	private JsonElement getJsonResponse(URL URLSource) throws CassandraIoException{
	
		JsonElement jse = null;
		BufferedReader in;
		
		try{
			InputStream connStream = null;
			HttpURLConnection conn = getApiConnection().getAPIConnection(URLSource, method, params, token, accountId);
			try{
				connStream = conn.getInputStream();
			}catch(IOException ioe){
				connStream = conn.getErrorStream();
			}
			in = new BufferedReader(new InputStreamReader(connStream, "UTF-8"));
			jse = new JsonParser().parse(in);
			in.close();
		}catch(Exception e){
			throw new CassandraIoException("Exception in getJsonElement", e);
		}
		
		return jse;
	}
	
	
	private URL constructAPIUrl(String apiType, String id) throws MalformedURLException, URISyntaxException{
		
		StringBuffer sb = new StringBuffer(getApiUrl());
		String scheme = "http";
		if(sb.toString().startsWith("https")){
			scheme = "https";
		}
		
		String[] pathQuery = id.split("\\?");
		String path = pathQuery[0];
		String query = null;
		if(pathQuery.length > 1){
			query = pathQuery[1];
		}
		
		URI uri = new URI(
			    scheme,
			    sb.toString().replaceFirst("https?://", ""), 
			    "/"+ APIConstants.API_VERSION +"/"+ apiType +"/"+ path,
			    query, null);
		
		log.info("API URL: "+ uri.toString());
		
		return uri.toURL();
		
	}

	/**
	 * Builds request POST params with a given map and encoding
	 * null encoding will not encode the value
	 * @param fieldMap
	 * @param encoding
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String buildRequestString(Map<String, String> fieldMap,
			String encoding) throws UnsupportedEncodingException {
		String data = "";
		Set<Entry<String, String>> entrySet = fieldMap.entrySet();
		for (Entry<String, String> entry : entrySet) {
			String colName = entry.getKey();
			String colValue = entry.getValue();
			if (encoding != null) {
				data += colName + "="
						+ URLEncoder.encode(colValue, encoding) + "&";
			} else {
				data += colName + "=" + colValue + "&";
			}
		}

		return data;

	}
	
	/**
	 * Creates a path from a number of string segments
	 * @param segments
	 * @return
	 *
	 */
	protected String pathBuilder(String... segments) {
		
		StringBuffer path = new StringBuffer();
		for(String seg : segments){
			if(seg != null){
				path.append(seg);
				path.append("/");
			}
		}
		return path.toString();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getMethod() {
		return method;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getParams() {
		return params;
	}

	public void setApiConnection(APIConnection apiConnection) {
		this.apiConnection = apiConnection;
	}

	public APIConnection getApiConnection() {
		return apiConnection;
	}

	
}
