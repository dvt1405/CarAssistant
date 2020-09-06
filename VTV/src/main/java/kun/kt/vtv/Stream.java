package kun.kt.vtv;

public class Stream {
	private String bandwidth;
	private String resolution;
	private String link;
	public Stream(String bandwidth, String resolution, String link) {
		super();
		this.bandwidth = bandwidth;
		this.resolution = resolution;
		this.link = link;
	}
	public String getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
}
