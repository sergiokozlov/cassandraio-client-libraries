package io.cassandra.sdk.cql;

import io.cassandra.sdk.CassandraIoSDK;
import io.cassandra.sdk.conn.APIConnection;
import io.cassandra.sdk.constants.APIConstants;
import io.cassandra.sdk.data.DataMapModel;
import io.cassandra.sdk.exception.CassandraIoException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * 
 * @author cchiappone
 *
 */
public class CqlAPI extends CassandraIoSDK {

	public static String API_CALL = APIConstants.API_CQL;

	public CqlAPI(APIConnection apiConnection, String apiUrl, String token,
			String accountId) {
		super(apiConnection, apiUrl, token, accountId);
		
	}

	public CqlAPI(String apiUrl, String token, String accountId) {
		super(apiUrl, token, accountId);
		
	}
	
	/**
	 * Allows passing CQL Select queries <br/>
	 * Note: 'FROM' should not be included since columnFamilyName is included in the method signature.
	    <div><b>HTTP CQL GET</b></div></br> 
		Syntax<code> /v0.3/CQL/CloudFamily?query=SELECT * WHERE {index} = {value}</code><br/> 
		Example<code>/v0.3/CQL/CloudFamily?query=SELECT * WHERE tempScale = F</code><br/> OR<br/> 
		Syntax<code> /v0.3/CQL/CloudFamily?query=SELECT * WHERE KEY = {rowKey} AND {index} = {value}</code><br/> 
		Syntax<code> /v0.3/CQL/CloudFamily?query=SELECT * WHERE KEY = chris@isidorey.com AND name = chris</code><br/> 
	 * @param keyspaceName
	 * @param columnFamilyName
	 * @param cqlQuery
	 * @return
	 * @throws CassandraIoException
	 */
	public CqlMapModel executeCqlQuery(String keyspaceName, String columnFamilyName, String cqlQuery, boolean csvFormat)
		throws CassandraIoException {
		
		Gson gson = new Gson();
		JsonObject jso = null;
		try{
			
			String path = pathBuilder(keyspaceName, columnFamilyName);
			path = path + "?query="+ cqlQuery;
		
			if(csvFormat){
				API_CALL = API_CALL + ".csv";
				
			}else{
				jso = getJsonResponse(API_CALL, path, METH_GET).getAsJsonObject();
			}
			
			CqlMapModel cmm = new CqlMapModel();
			if(jso != null){
				Map<String,DataMapModel> dataMap = new LinkedHashMap<String,DataMapModel>();
				for(Entry<String,JsonElement> entry :jso.entrySet()){
					String rowKey = entry.getKey();
					DataMapModel dmm = gson.fromJson(entry.getValue(), DataMapModel.class);
					dataMap.put(rowKey, dmm);
				}
				cmm.setCqlMap(dataMap);
			}
			
			return cmm;
			
		}catch(Exception e){
			try{
				return gson.fromJson(jso, CqlMapModel.class);
			}catch(Exception e2){
				throw new CassandraIoException(e);
			}
		}	
		
	}
	
}
