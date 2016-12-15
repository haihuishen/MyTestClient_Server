package com.shen.client.tools;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class CustomerHttpClient {
	
	private static HttpClient client;
	
	public static synchronized HttpClient getHttpClient(){
		if(client == null) {
			/**
			 * 设置你的请求参数，配置
			 */
//			HttpParams params = new BasicHttpParams();
			
			
			client = new DefaultHttpClient(null, null);
			
		}
		return client;
	}

}
