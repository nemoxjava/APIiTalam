package com.italam.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.io.IOUtils;





public class RestUtils {
	
	private final String USER_AGENT = "Mozilla/5.0";
	
	
	public static void main(String[] args) throws Exception {
		String json_args = "{\"username\":\"Admin\",\"password\":\"!Ad1talam\"}";
		String https_url = "https://dev.italam.org/lms/local/compedia_services/mainservice.php?action=login&service_type=ums&args=" + URLEncoder.encode(json_args,"UTF-8");
		
		String token = restApiHttps(json_args,https_url);
		System.out.println(token);
		
		https_url = "https://dev.italam.org/lms/local/compedia_services/mainservice.php?action=getInitialData&service_type=ums";
		String generalData = restApiHttps("",https_url);
		System.out.println(generalData);
		
		
//		RestUtils http = new RestUtils();
		 
		System.out.println("GET Request Using HttpURLConnection");
		//http.sendGet1();
//		String url = "https://dev.italam.org/lms/local/compedia_services/mainservice.php?action=getInitialData&service_type=ums";
//		http.sendGet1(url);
//      System.out.println();
//		System.out.println("POST Request Using HttpURLConnection");
//		http.sendPost();
	}
	
	
	public static String restApiHttp(String json_args, String https_url) throws IOException {
	
			//initiate HTTP-GET request
			URL url = new URL(https_url);		
		    HttpURLConnection con = (HttpURLConnection)url.openConnection();

		    
		    //Read Output Stream
	        InputStream content = (InputStream) con.getInputStream();        
	        StringWriter writer = new StringWriter();
	        IOUtils.copy(content, writer, "UTF-8");
	        String theString = writer.toString();
			 
			//System.out.println(theString);
			return theString;
	}
	
	public static String restApiHttps(String json_args, String https_url) throws IOException {
		
		//initiate HTTPS-GET request
		URL url = new URL(https_url);		
	    HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

	    
	    //Read Output Stream
        InputStream content = (InputStream) con.getInputStream();        
        StringWriter writer = new StringWriter();
        IOUtils.copy(content, writer, "UTF-8");
        String theString = writer.toString();
		 
		//System.out.println(theString);
		return theString;
}

	
	public static String restApiHttps(String json_args, String https_url, String encoding) throws IOException {
		
		//initiate HTTPS-GET request
		URL url = new URL(https_url);		
	    HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

	    
	    //Read Output Stream
        InputStream content = (InputStream) con.getInputStream();        
        StringWriter writer = new StringWriter();
        IOUtils.copy(content, writer, encoding);
        String theString = writer.toString();
		 
		//System.out.println(theString);
		return theString;
}

	public static String restApiHttps(String https_url) throws IOException {
		
		//initiate HTTPS-GET request
		URL url = new URL(https_url);		
	    HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

	    
	    //Read Output Stream
        InputStream content = (InputStream) con.getInputStream();        
        StringWriter writer = new StringWriter();
        IOUtils.copy(content, writer, "UTF-8");
        String theString = writer.toString();
		 
		//System.out.println(theString);
		return theString;
}
	
	
	// HTTP GET request
	private void sendGet1(String url) throws Exception {
	    
		url = "https://dev.italam.org/lms/local/compedia_services/mainservice.php?action=getInitialData&service_type=ums";
		
//		String url = "https://twitter.com/search";
//	    String username="hitenpratap";
//        StringBuilder stringBuilder = new StringBuilder("https://twitter.com/search");
//        stringBuilder.append("?q=");
//        stringBuilder.append(URLEncoder.encode(username, "UTF-8"));
        
        StringBuilder stringBuilder1 = new StringBuilder(url); 
        
        URL obj = new URL(stringBuilder1.toString());
 
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Charset", "utf-8");
 
		System.out.println("\nSending request to URL : " + url);
		System.out.println("Response Code : " + con.getResponseCode());
		System.out.println("Response Message : " + con.getResponseMessage());
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String line;
		StringBuffer response = new StringBuffer();
 
		while ((line = in.readLine()) != null) {
			response.append(line);
		}
		in.close();
 
		System.out.println(response.toString());
 
	}
 
	private void sendPost() throws Exception {
	    
	    StringBuilder tokenUri=new StringBuilder("param1=");
        tokenUri.append(URLEncoder.encode("params1","UTF-8"));
        tokenUri.append("&param2=");
        tokenUri.append(URLEncoder.encode("param2","UTF-8"));
        tokenUri.append("&param3=");
        tokenUri.append(URLEncoder.encode("param3","UTF-8"));
 
		String url = "https://example.com";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "UTF-8");
 
		con.setDoOutput(true);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
       // outputStreamWriter.write(params.toString());
        outputStreamWriter.flush();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		System.out.println(response.toString());
 
	}
 

	
	
}
