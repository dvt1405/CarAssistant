/**
 * 工程名:VoiceSettings
 * 文件名:FinalAir.java
 * 包   名:com.syu.air
 * 日   期:2016年3月25日下午2:52:12
 * 作   者:fyt 
 * Copyright (c) 2016, kexuan52@yeah.net All Rights Reserved.
 *
 */
package kun.kt.opencam.air;

/**
 *类   名:FinalAir
 *功   能:TODO
 *
 *日  期:2016年3月25日 下午2:52:12
 * @author fyt
 *
 */
public class FinalAir {
	
	public static final int VA_CMD_AIR_BEGAIN			= 33;	// 空调控制
	public static final int VA_CMD_AIR_POWER_ON			= VA_CMD_AIR_BEGAIN+0;	// 空调开关
	public static final int VA_CMD_AIR_AC_ON			= VA_CMD_AIR_BEGAIN+1;	// AC开关
	public static final int VA_CMD_AIR_AUTO_ON			= VA_CMD_AIR_BEGAIN+2;	// AUTO开关 new
	public static final int VA_CMD_AIR_FRONT_DEFROST_ON	= VA_CMD_AIR_BEGAIN+3;	// 前除霜
	public static final int VA_CMD_AIR_REAR_DEFROST_ON	= VA_CMD_AIR_BEGAIN+4;	// 后除霜
	public static final int VA_CMD_AIR_CYCLE_TYPE		= VA_CMD_AIR_BEGAIN+5;	// 内外循环
	public static final int VA_CMD_AIR_BLOW_LEVEL		= VA_CMD_AIR_BEGAIN+6; // 吹风等级/左吹风等级（-2：等级减-1：等级加  0~7+，具体的等级，一般是0~7）
	public static final int VA_CMD_AIR_BLOW_LEVEL_RIGHT	= VA_CMD_AIR_BEGAIN+7; // 右吹风等级（-2：等级减 -1：等级加 0~7+，具体的等级，一般是0~7）
	public static final int VA_CMD_AIR_TEMP_LEFT		= VA_CMD_AIR_BEGAIN+8; // 左温度（0：温度减 1：温度加），待验证
	public static final int VA_CMD_AIR_TEMP_RIGHT		= VA_CMD_AIR_BEGAIN+9; // 右问题（0：温度减 1：温度加），待验证
//	public static final int VA_CMD_AIR_ACMAX_ON			= VA_CMD_AIR_BEGAIN+2;	// AC MAX开关
//	public static final int VA_CMD_AIR_BLOW_WIND_ON		= VA_CMD_AIR_BEGAIN+7;	// 吹窗/左吹窗/吹头/左吹头
//	public static final int VA_CMD_AIR_BLOW_BODY_ON		= VA_CMD_AIR_BEGAIN+8;	// 吹身/左吹身
//	public static final int VA_CMD_AIR_BLOW_FOOT_ON		= VA_CMD_AIR_BEGAIN+9;	// 吹脚/左吹脚/吹下/左吹下
//	public static final int VA_CMD_AIR_BLOW_WIND_ON_RIGHT= VA_CMD_AIR_BEGAIN+10;// 右吹窗/右吹窗/右吹头/右吹头（预留）
//	public static final int VA_CMD_AIR_BLOW_BODY_ON_RIGHT= VA_CMD_AIR_BEGAIN+11;// 右吹身/右吹身（预留）
//	public static final int VA_CMD_AIR_BLOW_FOOT_ON_RIGHT= VA_CMD_AIR_BEGAIN+12;// 右吹脚/右吹脚/右吹下/右吹下（预留）
//	public static final int VA_CMD_AIR_DUAL_ON			= VA_CMD_AIR_BEGAIN+17;	// DUAL单区双区
	public static final int VA_CMD_AIR_TEMP_LEFT_DIRECT			= VA_CMD_AIR_BEGAIN + 22;
	public static final int VA_CMD_AIR_TEMP_RIGHT_DIRECT		= VA_CMD_AIR_BEGAIN + 23;
	
	
	public static final int VA_CMD_AIR_END				=  VA_CMD_AIR_BEGAIN+30;	// 空调开关
	
	public static final int VA_CMD_DJ_PENGPAI			= VA_CMD_AIR_END+3;		// 德众澎湃动力
	public static final int VA_CMD_360_FUNCTION			= VA_CMD_AIR_END+4;		// 360功能
	public static final int VA_CMD_WINDOW_FUNCTION		= VA_CMD_AIR_END+5;		// 车窗
	
	
	
	public static final int ENGINE_ECO			=  0;
	public static final int ENGINE_STD			=  ENGINE_ECO + 1;
	public static final int ENGINE_POWER			=  ENGINE_ECO + 5;
	public static final int ENGINE_TRACK			=  ENGINE_ECO + 4;
	public static final int ENGINE_SPORT			=  ENGINE_ECO + 3;
	public static final int ENGINE_COMFORT			=  ENGINE_ECO +2;
	
	public static final int VA_TRUNK_CONTROL_CMD		= VA_CMD_AIR_END+1;		// 尾门开关
	
	
	public static final int ECARD_ACTION_ID = 200;
//	public static final int ECARD_ACTION_PAYMENT = 200;
//	public static final int ECARD_ACTION_HELP = ECARD_ACTION_PAYMENT + 1;;
//	public static final int ECARD_ACTION_CHAT = ECARD_ACTION_PAYMENT + 2;;
//	public static final int ECARD_ACTION_NAVI= ECARD_ACTION_PAYMENT + 1;;
	
	// 空调循环类型
	public static final int CYCLE_OUTER			= 0;//外循环
	public static final int CYCLE_INNER_MANUAL	= 1;//内循环
	public static final int CYCLE_AUTO			= 2;//自动
}
