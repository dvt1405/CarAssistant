/**
 * 工程名:ShaVoiceService
 * 文件名:Contasts.java
 * 包   名:com.syu.sha
 * 日   期:2015年11月23日下午5:13:05
 * 作   者:fyt 
 * Copyright (c) 2015, kexuan52@yeah.net All Rights Reserved.
 *
 */
package kun.kt.opencam.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *类   名:Contasts
 *功   能:TODO
 *
 *日  期:2015年11月23日 下午5:13:05
 * @author fyt
 *
 */
public class Contasts implements Parcelable {
	String name;
	String number;
	
	public Contasts(String name, String number) {
		this.name = name;
		this.number = number;
	}
	
	public Contasts(Parcel in) {
		name = in.readString();
		number = in.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(name);
		out.writeString(number);
	}
	
	final public static Creator<Contasts> CREATOR = new Creator<kun.kt.opencam.ipc.Contasts>() {
		
		@Override
		public Contasts[] newArray(int size) {
			return null;
		}
		
		@Override
		public Contasts createFromParcel(Parcel source) {
			return new Contasts(source);
		}
	};
	
	public boolean equals(Object o) {
		if(o instanceof Contasts) {
			Contasts other = (Contasts) o;
			return isEquals(name, other.name) 
					&& isEquals(number, other.number);
		}
		return super.equals(o);
	};
	
	public static boolean isEquals (String src, String des) {
		if(src == null || des == null)
			return false;
		
		return src.equals(des);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
}
