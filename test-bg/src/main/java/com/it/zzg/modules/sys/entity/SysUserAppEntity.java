package com.it.zzg.modules.sys.entity;

import java.io.Serializable;


/**
 * 用户与APP对应关系
 * 
 * @author admin
 * @email admin
 * @date 2018-07-07 18:57:35
 */
public class SysUserAppEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//用户ID
	private Long userId;
	//角色ID
	private Long appId;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：角色ID
	 */
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	/**
	 * 获取：角色ID
	 */
	public Long getAppId() {
		return appId;
	}
}
