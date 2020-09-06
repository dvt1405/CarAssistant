/**
 * 工程名:ShaVoiceService
 * 文件名:Song.java
 * 包   名:com.syu.sha.ipc
 * 日   期:2015年12月15日下午5:38:34
 * 作   者:fyt 
 * Copyright (c) 2015, kexuan52@yeah.net All Rights Reserved.
 *
 */
package kun.kt.opencam.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *类   名:Song
 *功   能:TODO
 *
 *日  期:2015年12月15日 下午5:38:34
 * @author fyt
 *
 */
public class Song implements Parcelable {
	String title;
	String path;
	
	public Song(String title, String path) {
		this.title = title;
		this.path = path;
	}
	
	/**
	 * 
	 */
	public Song(Parcel in) {
		title = in.readString();
		path = in.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(title);
		out.writeString(path);
	}
	
final public static Creator<Song> CREATOR = new Creator<kun.kt.opencam.ipc.Song>() {
		
		@Override
		public Song[] newArray(int size) {
			return null;
		}
		
		@Override
		public Song createFromParcel(Parcel source) {
			return new Song(source);
		}
	};
	
	public boolean equals(Object o) {
		if(o instanceof Song) {
			Song other = (Song) o;
			return isEquals(title, other.title) 
					&& isEquals(path, other.path);
		}
		return super.equals(o);
	};
	
	public static boolean isEquals (String src, String des) {
		if(src == null || des == null)
			return false;
		
		return src.equals(des);
	}
	
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
}
