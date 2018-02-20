package com.srct.ril.poas.ai.baidunlp;

import org.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.util.Utils;
import com.baidu.aip.nlp.AipNlp;
import com.srct.ril.poas.utils.Log;

@Configuration
@ConfigurationProperties(prefix = "baidu")
public class BaiduClient {
	private String app_id;
    private String api_key;
	private String secret_key;
	
	private int connection_timeout_millis;
	private int socket_timeout_millis;
	
	private AipNlp client;
	
	
	// 初始化一个AipNlp
	public AipNlp getClient() {
		if(client == null) {
			Log.i(this.getClass(), "getClient");
			client = new AipNlp(app_id, api_key, secret_key);
		    client.setConnectionTimeoutInMillis(2000);
		    client.setSocketTimeoutInMillis(60000);
		}
	    return client;
	}


	/**
	 * @return the app_id
	 */
	public String getApp_id() {
		return app_id;
	}


	/**
	 * @param app_id the app_id to set
	 */
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}


	/**
	 * @return the api_key
	 */
	public String getApi_key() {
		return api_key;
	}


	/**
	 * @param api_key the api_key to set
	 */
	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}


	/**
	 * @return the secret_key
	 */
	public String getSecret_key() {
		return secret_key;
	}


	/**
	 * @param secret_key the secret_key to set
	 */
	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}


	/**
	 * @return the connection_timeout_millis
	 */
	public int getConnection_timeout_millis() {
		return connection_timeout_millis;
	}


	/**
	 * @param connection_timeout_millis the connection_timeout_millis to set
	 */
	public void setConnection_timeout_millis(int connection_timeout_millis) {
		this.connection_timeout_millis = connection_timeout_millis;
	}


	/**
	 * @return the socket_timeout_millis
	 */
	public int getSocket_timeout_millis() {
		return socket_timeout_millis;
	}


	/**
	 * @param socket_timeout_millis the socket_timeout_millis to set
	 */
	public void setSocket_timeout_millis(int socket_timeout_millis) {
		this.socket_timeout_millis = socket_timeout_millis;
	}

}
