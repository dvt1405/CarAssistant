/**
 * 工程名:ShaVoiceService
 * 文件名:TransitConst.java
 * 包   名:com.syu.sha.ipc
 * 日   期:2015年11月24日上午8:39:50
 * 作   者:fyt 
 * Copyright (c) 2015, kexuan52@yeah.net All Rights Reserved.
 *
 */
package kun.kt.opencam.ipc;

/**
 *类   名:TransitConst
 *功   能:TODO
 *
 *日  期:2015年11月24日 上午8:39:50
 * @author fyt
 *
 */
public class TransitConst {
	
	public static final int MACHINE_POWER_ON = 0;
	//蓝牙状态
	public static final int PHONE_STATE_DISCONNECTED		= 0;
	public static final int PHONE_STATE_LINK				= 1;
	public static final int PHONE_STATE_CONNECTED			= 2;
	public static final int PHONE_STATE_DIAL				= 3;
	public static final int PHONE_STATE_RING				= 4;
	public static final int PHONE_STATE_TALK				= 5;
	public static final int PHONE_STATE_PAIR				= 6;	// 配对中
	
	//主机状态
	// APP类型
//	public static final int MCU_STATE_LAST				= -2; 	// ?
	public static final int APP_ID_LAST					= -1;	// 上一个应用
	public static final int APP_ID_NULL					= 0;	// 无
	public static final int APP_ID_RADIO				= 1;	// 收音机
	public static final int APP_ID_BTPHONE				= 2;	// 蓝牙电话
	public static final int APP_ID_BTAV					= 3;	// 蓝牙音频
	public static final int APP_ID_DVD					= 4;	// DVD
	public static final int APP_ID_AUX					= 5;	// AUX
	public static final int APP_ID_TV					= 6;	// 电视
	public static final int APP_ID_IPOD					= 7;	// 苹果设备
	public static final int APP_ID_AUDIO_PLAYER			= 8;	// 音频播放器
	public static final int APP_ID_VIDEO_PLAYER			= 9;	// 视频播放器
	public static final int APP_ID_THIRD_PLAYER			= 10;	// 第三方播放器
	public static final int APP_ID_CAR_RADIO			= 11;	// 原车收音机
	public static final int APP_ID_CAR_BTPHONE			= 12;	// 原车蓝牙
	public static final int APP_ID_CAR_USB				= 13;	// 原车USB
	public static final int APP_ID_DVR					= 14;	// DVR
	
	
}
