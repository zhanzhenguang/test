package com.it.zzg.common.utils;

/**
 * 常量
 * 
 * @author admin
 * @email admin
 * @date 2016年11月15日 下午1:23:52
 */
public class Constant {
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
	public static final String USER_PREX = "user_";
	/** 系统 **/
	public enum Sys{
		/**
		 * 商城系统登录
		 */
		SHOP(1);
		
		private int value;

		Sys(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
	}

	/** 系统业务角色 **/
	public enum Role{
		/**
		 * 业务操作员
		 */
		OPERATION(1),
		/**
		 * 业务管理员
		 */
		BUSI_ADMIN(2);
		
		private int value;

		Role(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
	}
	/** 保存类型 **/
	public enum SaveType{
		/**
		 * 保存
		 */
		SAVE(1),
		/**
		 * 更新
		 */
		UPDATE(2);
		
		private int value;

		SaveType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
	}
	/** 订单类型 **/
	public enum ServiceStatus{
		/**
		 * 未受理
		 */
		NOT_DEAL(0),
		/**
		 * 已经受理
		 */
		AL_DEAL(1),
		/**
		 * 重复申请
		 */
		REPEAT(2),
		/**
		 * 已经结款
		 */
		AL_PAY(3);
		
		private int value;

		ServiceStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
	}
	/** 数据状态 **/
	public enum Status{
		/**
		 * 正常
		 */
		NORMAL(1),
		/**
		 * 禁止
		 */
		FOBIDDEN(2);
		
		private int value;

		Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
	}
	/**
	 * 菜单类型
	 * 
	 * @author admin
	 * @email admin
	 * @date 2016年11月15日 下午1:24:29
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     * 
     * @author admin
     * @email admin
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    //流程编码
    public static final String TO_SIGN_FLOW_CODE = "to_sign";
    public static final String TO_RENEWSIGN_FLOW_CODE = "to_renewSign";
    public static final String TO_BREAKSIGN_FLOW_CODE = "to_breakSign";
    public static final String TO_CHANGE_FLOW_CODE = "to_change";
    
    public static final String CONT_STATUS_0 = "0";//草稿
    public static final String CONT_STATUS_1 = "1";//待审批
    public static final String CONT_STATUS_2 = "2";//待生效
    public static final String CONT_STATUS_3 = "3";//已生效
    public static final String CONT_STATUS_4 = "4";//已废除
    public static final String CONT_STATUS_5 = "5";//已解约
    public static final String CONT_STATUS_6 = "6";//已结束
    public static final String CONT_STATUS_7 = "7";//已续约
    public static final String CONT_STATUS_8 = "8";//已变更

    public static final String STATUS_1 = "1";//正常
    public static final String STATUS_2 = "2";//删除
    public static final String STATUS_3 = "3";//时间排序
    
    public static final String FORM_ID_SUFFIX_JY = "JY";//解约
    public static final String FORM_ID_SUFFIX_XY = "XY";//续约
    public static final String FORM_ID_SUFFIX_BG = "BG";//变更
}
