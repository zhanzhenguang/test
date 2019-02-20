package com.it.zzg.modules.flow.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 流程执行角色用户中间表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:25
 */
public class ExecuteRoleUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//用户id
	private String userId;
	//节点执行角色
	private String executeRole;
	private String flowCode;
	

	public String getFlowCode() {
		return flowCode;
	}
	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}
	/**
	 * 设置：节点执行角色
	 */
	public void setExecuteRole(String executeRole) {
		this.executeRole = executeRole;
	}
	/**
	 * 获取：节点执行角色
	 */
	public String getExecuteRole() {
		return executeRole;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public String getUserId() {
		return userId;
	}
}
