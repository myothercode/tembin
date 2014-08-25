package com.base.utils.httpclient;

import com.mysql.jdbc.ExceptionInterceptor;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.*;

public class HttpClientUtil {
	//private static Logger logger = Logger.getLogger(HttpClientUtil.class);
    static Logger log= Logger.getLogger(ExceptionInterceptor.class);
	private static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 6.0; zh-CN; rv:1.9.1.8) Gecko/20100202 Firefox/3.5.8 (.NET CLR 3.5.30729)";

	public static HttpClient getHttpClient() {
		// Create and initialize HTTP parameters
		HttpParams params = new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(params, 100);
		// ConnManagerParams.setTimeout(params, 60*1000);

		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		params.setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
		params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				10 * 1000);
		params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 60 * 1000);
		// Create and initialize scheme registry
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));

		// Create an HttpClient with the ThreadSafeClientConnManager.
		// This connection manager must be used if more than one thread will
		// be using the HttpClient.
		ClientConnectionManager cm = new ThreadSafeClientConnManager(params,
				schemeRegistry);
		HttpClient httpClient = new DefaultHttpClient(cm, params);
		// httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS,
		// false);

		return httpClient;
	}

	public static HttpClient getHttpsClient() {

		HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
		HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

		SchemeRegistry registry = new SchemeRegistry();
		SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
		socketFactory
				.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
		registry.register(new Scheme("https", socketFactory, 443));

		// httpClient = new DefaultHttpClient();

		HttpParams params = new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(params, 100);
		// ConnManagerParams.setTimeout(params, 3*60*1000);
		params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				10 * 1000);
		params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 60 * 1000);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

		// Create and initialize scheme registry
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));

		schemeRegistry.register(new Scheme("https", socketFactory, 443));
		ClientConnectionManager cm = new ThreadSafeClientConnManager(params,
				schemeRegistry);
		HttpClient httpClient = new DefaultHttpClient(cm, params);

		// 设置自动跳转为false
		// ((AbstractHttpClient)httpClient).setRedirectHandler(new
		// DummyRedirectHandler());

		return httpClient;

	}

	private static String getResponse(InputStream is, String encode)
			throws Exception {
		BufferedReader br = null;
		StringBuffer html = new StringBuffer();
		try {
			br = new BufferedReader(new InputStreamReader(is, encode));

			String tempbf;

			while ((tempbf = br.readLine()) != null) {
				html.append(tempbf + "\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null)
				br.close();
		}
		return html.toString();
	}

	public static String get(HttpClient httpClient, String url) throws Exception {
		return get(httpClient, url, "utf-8");
	}

	public static String get(HttpClient httpClient, String url, String encode) throws Exception {
		BufferedReader br = null;
		StringBuffer html = new StringBuffer();
		try {
			HttpGet httpget = new HttpGet(url.replaceAll("&amp;", "&")
					.replaceAll("\\s*$", ""));
			httpget.addHeader("User-Agent", USER_AGENT);
			InputStream is = httpClient.execute(httpget).getEntity()
					.getContent();
			br = new BufferedReader(new InputStreamReader(is, encode));

			String tempbf;

			while ((tempbf = br.readLine()) != null) {
				html.append(tempbf + "\r\n");
			}
		} catch (Exception e) {
			//logger.error(e.getMessage(), e);
		} finally {
			if (br != null)
				br.close();
		}
		return html.toString();
	}

	public static String post(HttpClient httpClient, String url) throws Exception {
		return post(httpClient, url, null);
	}

	public static String post(HttpClient httpClient, String url, String content) throws Exception {
		return post(httpClient, url, content, "utf-8",null);
	}

	public static String post(HttpClient httpClient, String url, String content, String encode,List<BasicHeader> header)
			throws Exception {
		String ret = "";
		try {
			HttpPost httppost = new HttpPost(url.replaceAll("&amp;", "&")
					.replaceAll("\\s*$", ""));
			httppost.addHeader("User-Agent", USER_AGENT);

            /*测试数据*/
            /*httppost.addHeader("X-EBAY-API-COMPATIBILITY-LEVEL","879");
            httppost.addHeader("X-EBAY-API-DEV-NAME","bbafa7e7-2f98-4783-9c34-f403faeb007f");
            httppost.addHeader("X-EBAY-API-APP-NAME","chengdul-5b82-4a84-a496-6a2c75e4e0d5");
            httppost.addHeader("X-EBAY-API-CERT-NAME","724f4467-9280-437c-a998-f5bcfee67ce5");
            httppost.addHeader("X-EBAY-API-SITEID","0");
            httppost.addHeader("X-EBAY-API-CALL-NAME","GeteBayOfficialTime");*/
            if(header!=null && !header.isEmpty()){
                for (Header h : header){
                    httppost.addHeader(h);
                }
            }

			if (content == null) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				httppost.setEntity(new UrlEncodedFormEntity(nvps, encode));
			} else {
				StringEntity myEntity = new StringEntity(content, encode);
				httppost.setEntity(myEntity);
			}

			HttpResponse response = httpClient.execute(httppost);

			ret = getResponse(response.getEntity().getContent(), encode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

}
