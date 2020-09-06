package kun.kt.vtv;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;


public class HTTPClient {
	private static final Logger log = Logger.getLogger("HTTPClient");
	private HttpTransport HTTP_TRANSPORT;
	private JsonFactory JSON_FACTORY;

	public HTTPClient() {
		HTTP_TRANSPORT = new NetHttpTransport();
		JSON_FACTORY = new JacksonFactory();
	}

//	public static void main(String[] args) {
//		try {
//			HashMap<String, String> h = new HashMap<String, String>();
//			h.put("abc", "1");
//			h.put("xyz", "2");
//			HttpContent content = new UrlEncodedContent(h);
//			Gson gson = new Gson();
//			System.out.println(gson.toJson(content));
//
//			// String fbId = "10215001195323869";
//			// String url = "https://graph.facebook.com/v2.11/" + fbId
//			// + "/picture";
//			// HTTPClient c = new HTTPClient();
//			// c.getRequestURI(url);
//		} catch (Exception e) {
//		}
//	}

	public String getRequestURI(String url) throws Exception {
		log.info("Url: " + url);
		String response = null;
		HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			@Override
			public void initialize(HttpRequest request) {
				request.setParser(new JsonObjectParser(JSON_FACTORY));
			}
		});
		GenericUrl urlG = new GenericUrl(url);
		HttpRequest request = requestFactory.buildGetRequest(urlG);
		HttpResponse res = request.execute();
		response = res.getRequest().getUrl().toString();
		// HttpHeaders headers = res.getHeaders();
		// for(Entry<String, Object> e : headers.entrySet()) {
		// log.info(e.getKey()+":" + e.getValue());
		// }
		// response = (String)res.getHeaders().get("Request URL");
		log.info("Response:" + response);
		return response;

	}

	public String get(String url) throws Exception {
		log.info("Url: " + url);
		String response = null;
		HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			@Override
			public void initialize(HttpRequest request) {
				request.setParser(new JsonObjectParser(JSON_FACTORY));
			}
		});
		GenericUrl urlG = new GenericUrl(url);
		HttpRequest request = requestFactory.buildGetRequest(urlG);
		HttpResponse res = request.execute();
		// res.getHeaders().get("")
		response = res.parseAsString();
		res.disconnect();
		log.info("Response:" + response);
		return response;

	}

	public String get(String url, ArrayList<Object> setCookie) throws Exception {
		log.info("Url: " + url);
		String response = null;
		HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			@Override
			public void initialize(HttpRequest request) {
				request.setParser(new JsonObjectParser(JSON_FACTORY));
			}
		});
		GenericUrl urlG = new GenericUrl(url);
		HttpRequest request = requestFactory.buildGetRequest(urlG);
		request.getHeaders().set("X-Forwarded-For", "171.224.180.140");
		HttpResponse res = request.execute();
		// res.getHeaders().get("")
		@SuppressWarnings("unchecked")
		ArrayList<Object> temp = (ArrayList<Object>) res.getHeaders().get("set-cookie");
		setCookie.addAll(temp);
		response = res.parseAsString();
		res.disconnect();
//		log.info("Response:" + response);
		return response;

	}

	public String get(String url, HttpHeaders headers) throws Exception {
		log.info("Url: " + url);
		String response = null;
		HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			@Override
			public void initialize(HttpRequest request) {
				request.setParser(new JsonObjectParser(JSON_FACTORY));
			}
		});
		GenericUrl urlG = new GenericUrl(url);
		HttpRequest request = requestFactory.buildGetRequest(urlG);
		request.setHeaders(headers);
		HttpResponse res = request.execute();
		response = res.parseAsString();
		res.disconnect();
		log.info("Response:" + response);
		return response;

	}

	public String get(String url, boolean noLog) throws Exception {
		if (!noLog) {
			log.info("Url: " + url);
		}
		String response = null;
		HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			@Override
			public void initialize(HttpRequest request) {
				request.setParser(new JsonObjectParser(JSON_FACTORY));
			}
		});
		GenericUrl urlG = new GenericUrl(url);
		HttpRequest request = requestFactory.buildGetRequest(urlG);
		HttpResponse res = request.execute();
		// res.getHeaders().get("")
		response = res.parseAsString();
		res.disconnect();
		if (!noLog) {
			log.info("Response:" + response);
		}
		return response;

	}

	public String post(String url, String data) throws Exception {
		log.info("Data to send :" + data);
		log.info("Url: " + url);
		String response = null;
		HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			@Override
			public void initialize(HttpRequest request) {
				request.setParser(new JsonObjectParser(JSON_FACTORY));
			}
		});
		GenericUrl urlG = new GenericUrl(url);
		HttpContent content = ByteArrayContent.fromString("application/json", data);
		HttpRequest request = requestFactory.buildPostRequest(urlG, content);
		HttpResponse res = request.execute();
		response = res.parseAsString();
		res.disconnect();
		// log.info("Response:" + response);
		return response;

	}

	public String post(String url) throws Exception {
		HashMap<String, String> data = new HashMap<String, String>();
		return post(url, data);
	}

	public String post(String url, HashMap<String, String> data) throws Exception {
		log.info("Data to send :" + data);
		log.info("Url: " + url);
		String response = null;
		HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			@Override
			public void initialize(HttpRequest request) {
				request.setParser(new JsonObjectParser(JSON_FACTORY));
			}
		});
		GenericUrl urlG = new GenericUrl(url);
		HttpContent content = new UrlEncodedContent(data);
		HttpRequest request = requestFactory.buildPostRequest(urlG, content);
		HttpResponse res = request.execute();
		response = res.parseAsString();
		res.disconnect();
		log.info("Response:" + response);
		return response;

	}

	public String post(String url, String data, HttpHeaders headers) throws Exception {
		return post(url, data, headers, false);
	}

	public String post(String url, String data, HttpHeaders headers, boolean noLog) throws Exception {
		if (!noLog) {
			log.info("Data to send :" + data);
			log.info("Url: " + url);
		}
		String response = null;
		HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			@Override
			public void initialize(HttpRequest request) {
				request.setParser(new JsonObjectParser(JSON_FACTORY));
			}
		});
		GenericUrl urlG = new GenericUrl(url);
		HttpContent content = ByteArrayContent.fromString("application/json", data);
		HttpRequest request = requestFactory.buildPostRequest(urlG, content);
		request.setHeaders(headers);
		HttpResponse res = request.execute();
		response = res.parseAsString();
		res.disconnect();
		if (!noLog) {
			log.info("Response:" + response);
		}
		return response;
	}

	public String postFromData(String url, HashMap<String, String> data, HttpHeaders headers) throws Exception {
		log.info("Data to send :" + data);
		log.info("Url: " + url);
		String response = null;
		HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			@Override
			public void initialize(HttpRequest request) {
				request.setParser(new JsonObjectParser(JSON_FACTORY));
			}
		});
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> entry : data.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		String data1 = sb.toString();
		HttpContent content = new ByteArrayContent("application/x-www-form-urlencoded; charset=UTF-8",
				data1.getBytes());
		GenericUrl urlG = new GenericUrl(url);
		HttpRequest request = requestFactory.buildPostRequest(urlG, content);
		request.setHeaders(headers);
		HttpResponse res = request.execute();
		InputStream is = res.getContent();
		if (is != null) {
			byte[] buffer = new byte[10240];
			is.read(buffer);
			response = new String(buffer);

		}
		// response = res.parseAsString();
		res.disconnect();
		log.info("Response:" + response);
		return response;

	}
}
