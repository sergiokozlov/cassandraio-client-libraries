package io.cassandra.sdk.conn;

import io.cassandra.sdk.constants.APIConstants;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import sun.misc.BASE64Encoder;


public class DefaultAPIConnection implements APIConnection {
	private Logger log = Logger.getLogger(DefaultAPIConnection.class.getName());

	public HttpURLConnection getAPIConnection(URL URLSource, String method,
			String params, String username, String password) throws IOException {
		
		HostnameVerifier hv = new HostnameVerifier() {
			@Override
			public boolean verify(String urlHostName, SSLSession session) {
				
				return true;
			}

		};
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
				
			}
		} };
		
		SSLContext sc = null;
		try {
			sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
		} catch (NoSuchAlgorithmException e) {
			log.log(Level.SEVERE, "", e);
		} catch (KeyManagementException e) {
			log.log(Level.SEVERE, "", e);
		}
		
		HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		
		
		HttpURLConnection conn = null;
		try{
			conn = (HttpsURLConnection) URLSource.openConnection();
		}catch(ClassCastException e){
			conn = (HttpURLConnection) URLSource.openConnection();
		}
		
		conn.setReadTimeout(30000);
		conn.setConnectTimeout(30000);
		if (method != null)
			conn.setRequestMethod(method);
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		if (username != null && password != null) {
			String userAuth = username + ":" + password;
			BASE64Encoder enc = new BASE64Encoder();
			String encodedAuthorization = enc.encode(userAuth.getBytes());
			conn.setRequestProperty("Authorization", "Basic "
					+ encodedAuthorization);
		}
		if (APIConstants.METH_POST.equals(method)) {
			if(params != null){
				OutputStreamWriter wr = null;
				wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write(params);
				wr.flush();
			}
		}

		return conn;

	}

	
}
