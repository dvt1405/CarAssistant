package kun.kt.vtv;

public class StreamResponse {
	private String date;

    private String channel_name;

    private StreamInfo[] stream_info;

    private String remoteip;

    private String ads_time;

    private String content_id;

    private String ads_tags;

    private String player_type;

    private String[] stream_url;

    private String chromecast_url;

    private String geoname_id;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}

	public StreamInfo[] getStream_info() {
		return stream_info;
	}

	public void setStream_info(StreamInfo[] stream_info) {
		this.stream_info = stream_info;
	}

	public String getRemoteip() {
		return remoteip;
	}

	public void setRemoteip(String remoteip) {
		this.remoteip = remoteip;
	}

	public String getAds_time() {
		return ads_time;
	}

	public void setAds_time(String ads_time) {
		this.ads_time = ads_time;
	}

	public String getContent_id() {
		return content_id;
	}

	public void setContent_id(String content_id) {
		this.content_id = content_id;
	}

	public String getAds_tags() {
		return ads_tags;
	}

	public void setAds_tags(String ads_tags) {
		this.ads_tags = ads_tags;
	}

	public String getPlayer_type() {
		return player_type;
	}

	public void setPlayer_type(String player_type) {
		this.player_type = player_type;
	}

	public String[] getStream_url() {
		return stream_url;
	}

	public void setStream_url(String[] stream_url) {
		this.stream_url = stream_url;
	}

	public String getChromecast_url() {
		return chromecast_url;
	}

	public void setChromecast_url(String chromecast_url) {
		this.chromecast_url = chromecast_url;
	}

	public String getGeoname_id() {
		return geoname_id;
	}

	public void setGeoname_id(String geoname_id) {
		this.geoname_id = geoname_id;
	}
    
}
