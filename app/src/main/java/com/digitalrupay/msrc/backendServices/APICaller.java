package com.digitalrupay.msrc.backendServices;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;

public class APICaller {
	String content;
	public APICaller() {
	}

		public String GetDataFromUrl(String url) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		InputStream is = null;
		String data = null;

		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line;
			if ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			data = sb.toString();
			Log.e("data",data);
		} catch (UnsupportedEncodingException e) {
			//Logger.e("Parsing Error", "Error getting data", e);
			e.printStackTrace();
			data = "Error:loading: Failed to load data";
		} catch (ClientProtocolException e) {
			//Logger.e("Parsing Error", "Error getting data", e);
			data = "Error:loading: Failed to load data";
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			//Logger.e("Parsing Error", "Error getting data", e);
			data = "Error:loading: Failed to load data";
			e.printStackTrace();
		}catch (IOException e) {
			// TODO: handle exception
			data = "Error:loading: Failed to load data";
			e.printStackTrace();
		}

		return data;
	}
//	public String postDataFromUrl(String Object, String uri){
//		try {
//			StringEntity entity = new StringEntity(Object);
//			HttpParams myParams = new BasicHttpParams();
//			HttpConnectionParams.setConnectionTimeout(myParams, 10000);
//			HttpConnectionParams.setSoTimeout(myParams, 10000);
//			HttpClient httpclient = new DefaultHttpClient(myParams);
//			HttpPost httppost = new HttpPost(uri);
//			httppost.setHeader("Content-type", "application/json");
//			StringEntity se = new StringEntity(Object);
//			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//			httppost.setEntity(se);
//			HttpResponse response = httpclient.execute(httppost);
//			content = EntityUtils.toString(response.getEntity());
//			Log.e("data",content);
//		}catch (UnsupportedEncodingException e) {
//			//Logger.e("Parsing Error", "Error getting data", e);
//			content = "Error";
//		} catch (ClientProtocolException e) {
//			//Logger.e("Parsing Error", "Error getting data", e);
//			content = "Error";
//		} catch (SocketTimeoutException e) {
//			//Logger.e("Parsing Error", "Error getting data", e);
//			content = "Error";
//		} catch (IOException e) {
//			content="Error";
//		}
//		return content;
//	}
//	public String putDataFromUrlPUT(String Object, String uri){
//		try {
//			StringEntity entity = new StringEntity(Object);
//			HttpParams myParams = new BasicHttpParams();
//			HttpConnectionParams.setConnectionTimeout(myParams, 10000);
//			HttpConnectionParams.setSoTimeout(myParams, 10000);
//			HttpClient httpclient = new DefaultHttpClient(myParams);
//			HttpPut httpput = new HttpPut(uri);
//			httpput.setHeader("Content-type", "application/json");
//			StringEntity se = new StringEntity(Object);
//			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//			httpput.setEntity(se);
//
//			HttpResponse response = httpclient.execute(httpput);
//			content = EntityUtils.toString(response.getEntity());
//			Log.e("data",content);
//		}catch (UnsupportedEncodingException e) {
//			//Logger.e("Parsing Error", "Error getting data", e);
//			content = "Error";
//		} catch (ClientProtocolException e) {
//			//Logger.e("Parsing Error", "Error getting data", e);
//			content = "Error";
//		} catch (SocketTimeoutException e) {
//			//Logger.e("Parsing Error", "Error getting data", e);
//			content = "Error";
//		} catch (IOException e) {
//			content="Error";
//		}
//		return content;
//	}
//	public String deleteDataFromUrlPUT(String uri){
//		try {
//			HttpParams myParams = new BasicHttpParams();
//			HttpConnectionParams.setConnectionTimeout(myParams, 10000);
//			HttpConnectionParams.setSoTimeout(myParams, 10000);
//			HttpClient httpclient = new DefaultHttpClient(myParams);
//			HttpDelete httpput = new HttpDelete(uri);
//			httpput.setHeader("Content-type", "application/json");
//			HttpResponse response = httpclient.execute(httpput);
//			content = EntityUtils.toString(response.getEntity());
//			Log.e("data",content);
//		}catch (UnsupportedEncodingException e) {
//			content = "Error";
//		} catch (ClientProtocolException e) {
//			//Logger.e("Parsing Error", "Error getting data", e);
//			content = "Error";
//		} catch (SocketTimeoutException e) {
//			//Logger.e("Parsing Error", "Error getting data", e);
//			content = "Error";
//		} catch (IOException e) {
//			content="Error";
//		}
//		return content;
//	}
}
