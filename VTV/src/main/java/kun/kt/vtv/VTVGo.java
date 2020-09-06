package kun.kt.vtv;

import android.util.Log;

import com.google.api.client.http.HttpHeaders;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;


//import org.jsoup.Jsoup;
//import org.jsoup.nodes.DataNode;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.google.api.client.http.HttpHeaders;
//import com.google.gson.Gson;


public class VTVGo {
    //    	private static Logger log = LoggerFactory.getLogger(VTVGo.class);
    private Gson gson = null;

    public VTVGo() {
        gson = new Gson();
    }

    public ArrayList<Stream> getLink(String id) {
        try {
            HTTPClient client = new HTTPClient();
            String getHomePage = "https://vtvgo.vn/trang-chu.html";
            ArrayList<Object> setCookie = new ArrayList<>();
            String response = client.get(getHomePage, setCookie);
            Document root = Jsoup.parse(response);
            Element script = root.body().child(1).child(0).child(0).child(1).child(0).child(1);
            DataNode dataNode = (DataNode) script.childNode(0);
            String s = dataNode.getWholeData();
            ArrayList<Stream> streams = getToken(s, id, client, setCookie);
            return streams;
//			res = new VTVGoResponse(BaseResponse.SUCCESS, "");
//			((VTVGoResponse)res).setStreams(streams);
        } catch (Exception e) {
            Log.e("Error", e.getMessage(), e);
        }
//		log.info(gson.toJson(res));
        return null;
    }

    private String processingCookie(ArrayList<Object> setCookie) {
        StringBuilder sb = new StringBuilder();
        for (Object o : setCookie) {
            String s = (String) o;
            String[] ss = s.split(";");
            sb.append(ss[0]).append("; ");
        }
        return sb.toString();
    }

    public ArrayList<Stream> getToken(String jsCode, String id, HTTPClient client, ArrayList<Object> setCookie) {
        ArrayList<Stream> streams = new ArrayList<>();
        jsCode = jsCode.replaceAll("\n", "").replaceAll("\t", "");
        String[] ss = jsCode.split("\\{");
        String time = "";
        String token = "";
        if (ss.length > 1) {
            ss = ss[1].split("\\}");
            ss = ss[0].split(";");
            for (String s : ss) {
                s = s.trim();
                if (s.startsWith("var ")) {
                    s = s.substring(4).trim();
                    if (s.startsWith("time")) {
                        time = s.split("=")[1].trim().replace("'", "");
                    }
                    if (s.startsWith("token")) {
                        token = s.split("=")[1].trim().replace("'", "");
                    }
                }
            }
        }
        String getStreamUrl = "https://vtvgo.vn/ajax-get-stream";
        HashMap<String, String> data = new HashMap<>();
        data.put("type_id", "1");
        data.put("id", id);
        data.put("time", time);
        data.put("token", token);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.put("X-Requested-With", "XMLHttpRequest");
            headers.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:78.0) Gecko/20100101 Firefox/78.0");
            headers.setAccept("*/*");
            headers.setAcceptEncoding("gzip, deflate, br");
            headers.set("Accept-Language", "en-US,en;q=0.5");
            headers.set("sec-fetch-site", "same-origin");
            headers.set("sec-fetch-mode", "cors");
            headers.set("sec-fetch-dest", "empty");
            headers.setCookie(processingCookie(setCookie));
            headers.set("referer", "https://vtvgo.vn/trang-chu.html");
            headers.set("origin", "https://vtvgo.vn");
//			headers.set("X-Forwarded-For", "113.190.233.57");
            String response = client.postFromData(getStreamUrl, data, headers).replace("\\", "").trim();

            StreamResponse sRes = gson.fromJson(response, StreamResponse.class);
            String playlist = sRes.getChromecast_url();
            response = client.get(playlist);
            ss = response.split("\n");
            int len = ss.length;
            for (int i = 0; i < len; i++) {
                if (ss[i].startsWith("#EXT-X-STREAM-INF:")) {
                    String s = ss[i].substring(18);
                    String[] tempS = s.split(",");
                    String bandwidth = null;
                    String resolution = null;
                    String link = playlist.replace("playlist.m3u8", ss[i + 1]);
                    for (String s1 : tempS) {
                        if (s1.startsWith("BANDWIDTH=")) {
                            bandwidth = s1.substring(10);
                        }
                        if (s1.startsWith("RESOLUTION=")) {
                            resolution = s1.substring(11);
                        }
                    }
                    streams.add(new Stream(bandwidth, resolution, link));
                    i++;
                }
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage(), e);
        }
        return streams;
    }

//    public static void main(String[] args) {
////		BaseResponse s = new VTVGo().getLink("6");
////		System.out.println(s);
//    }
}
