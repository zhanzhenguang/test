package com.it.zzg.modules;

import java.math.BigDecimal;

public interface Constants {
	
	public static String TOKEN = "passwordRetryCache";
	public static Integer TOKEN_TIMEOUT = null;
	public static Long ADMIN_ID = 1L;
	public static String orgName = "orgName";
	public static final String CACHE_CONFIG = "cacheConfig";
	
	
	public boolean CreateAcctRecJob = false;
	
	interface SYS_MSG{
		public static final String s400 = "系统异常，请联系管理员";
		public static final String s500 = "无法更新，已经存在相同编码";
	}
	
	/**
	 * 订单状态  0-未确认  1-未支付    2-待发货  3-待收货  4-已完成     44-删除
	 */
	interface ORDER_STATUS{
		public static final int WQR = 0;
		public static final int WZF = 1;
		public static final int DFH = 2;
		public static final int DSH = 3;
		public static final int YWC = 4;
		
		public static final int NOT = 44;
	}
	
	/**
	   	BigDecimal.setScale()方法用于格式化小数点
		setScale(1)表示保留一位小数，默认用四舍五入方式 
		setScale(1,BigDecimal.ROUND_DOWN)直接删除多余的小数位，如2.35会变成2.3 
		setScale(1,BigDecimal.ROUND_UP)进位处理，2.35变成2.4 
		setScale(1,BigDecimal.ROUND_HALF_UP)四舍五入，2.35变成2.4
		setScaler(1,BigDecimal.ROUND_HALF_DOWN)四舍五入，2.35变成2.3，如果是5则向下舍
	 */
	interface SETSCALE{
		public static final int newScale = 2;
		public static final int roundingMode = BigDecimal.ROUND_HALF_UP;
	}
	/**
	 * 节点
	 * @author Administrator
	 *
	 */
	interface NODE_SEQ{
		public static final String X = "pos_x";//下一节点1
		public static final String Y = "pos_y";//前一节点
		public static final String Z = "pos_z";//下一节点2
		public static final String I = "pos_i";//下一节点3
	}
	/**
	 * 节点状态 1-已经处理  2-未处理 3-待处理
	 * @author Administrator
	 *
	 */
	interface NODE_STATUS{
		public static final int S_1 = 1;//已经处理
		public static final int S_2 = 2;//未处理
		public static final int S_3 = 3;//待处理
	}
	/**
	 * 通用状态
	 * @author Administrator
	 *
	 */
	interface STATUS{
		public static final int S_0 = 0;//废止、拒绝、禁止
		public static final int S_1 = 1;//正常    1-未生成凭据  
		public static final int S_2 = 2;//完成    2-生成凭据
		public static final String S1_0 = "0";//废止、拒绝、禁止
		public static final String S1_1 = "1";//正常    1-未生成凭据  
		public static final String S1_2 = "2";//完成    2-生成凭据
		
	}
	/**
	 * 确认
	 * @author Administrator
	 *
	 */
	interface CONFIRM{
		public static final Integer C_1 = 1;//是
		public static final Integer C_2 = 2;//否
	}
	/**
	 *  1-开始 2-结束  3-普通  
	 * @author Administrator
	 *
	 */
	interface NODE_TYP{
		public static final Integer T_1 = 1; 
		public static final Integer T_2 = 2; 
		public static final Integer T_3 = 3; 
	}
	/**
	 * 计算方式  1-固定租金  2-保底+超额提成 (月销售超过**对超出部分加收提成) 3-保底租金+月抽成 (二者取高，根据销售分等级收取)4-保底租金+年抽成(二者取高，根据销售分等级收取)  5-纯抽成
	 * @author Robert_Zhan
	 *
	 */
	interface CAL_TYPE{
		public static final Integer T_1 = 1; 
		public static final Integer T_2 = 2; 
		public static final Integer T_3 = 3;
		public static final Integer T_4 = 4;
		public static final Integer T_5 = 5; 
	}
	
	/**
	 * 收取方式  1-月销售超过**对超出部分加收提成   2-二者取高，根据销售分等级收取
	 * @author Robert_Zhan
	 *
	 */
	interface REC_TYPE{
		public static final Integer T_1 = 1; 
		public static final Integer T_2 = 2; 
	}
	
	/**
	 * 状态  1-未处理    2-已处理
	 * @author Robert_Zhan
	 *
	 */
	interface REC_STATUS{
		public static final Integer S_1 = 1; 
		public static final Integer S_2 = 2; 
		public static final String SS_1 = "1"; 
		public static final String SS_2 = "2"; 
	}
	
	/**
	 * 费用类型代码 D-押金 R-租金  为空-其他  
	 * @author Robert_Zhan
	 *
	 */
	interface FEE_TYPE{
		public static final String D = "D";
		public static final String R = "R";
		public static final String QT = "";
	}
	
	/**
	 * 费用类型--from sysConfig
	 * @author Robert_Zhan
	 *
	 */
	public static final String CACHE_REC = "cacheRec";
	interface CACHE_REC_I{
		public static final String RENT = "subjectA";//租金
		public static final String RENTER_DEPOSIT = "subjectB";//租赁押金
		public static final String WATER_DEPOSIT = "subjectC";//水电押金
		public static final String BUILD_DEPOSIT = "subjectD";//施工保证金
		public static final String SERVICE_FEE_YEAR = "subjectE";//商场服务费-年
		public static final String SERVICE_FEE_MONTH = "subjectF";//商场服务费-月
		public static final String OTHER = "subjectG";//其他
		public static final String REC = "subjectRec";//应收账款科目
	}
}
