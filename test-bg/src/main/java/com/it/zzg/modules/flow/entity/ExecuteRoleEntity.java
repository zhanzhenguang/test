package com.it.zzg.modules.flow.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 流程执行角色表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:25
 */
public class ExecuteRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//节点执行角色
	private String executeRole;
	//节点执行角色名称
	private String executeRoleName;
	private String userId;
	private String userName;
	//节点编码
	private String node;
	//流程编码
	private String flow;
	//状态  0：禁用   1：正常
	private Integer status;
	//创建者ID
	private Long createUserId;
	//创建时间
	private Date createTime;
	//更新人id
	private Long updateUserId;
	//更新时间
	private Date updateTime;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 节点执行角色名称
	 * @return
	 */
	public String getExecuteRoleName() {
		return executeRoleName;
	}
	public void setExecuteRoleName(String executeRoleName) {
		this.executeRoleName = executeRoleName;
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
	 * 设置：节点编码
	 */
	public void setNode(String node) {
		this.node = node;
	}
	/**
	 * 获取：节点编码
	 */
	public String getNode() {
		return node;
	}
	/**
	 * 设置：流程编码
	 */
	public void setFlow(String flow) {
		this.flow = flow;
	}
	/**
	 * 获取：流程编码
	 */
	public String getFlow() {
		return flow;
	}
	/**
	 * 设置：状态  0：禁用   1：正常
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态  0：禁用   1：正常
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：创建者ID
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	/**
	 * 获取：创建者ID
	 */
	public Long getCreateUserId() {
		return createUserId;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：更新人id
	 */
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
	/**
	 * 获取：更新人id
	 */
	public Long getUpdateUserId() {
		return updateUserId;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
