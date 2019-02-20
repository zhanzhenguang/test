package com.it.zzg.modules.flow.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 流程执行历史
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-20 10:43:49
 */
public class ExecuteHisEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//节点编码
	private String node;
	private String nodeName;
	private String userName;
	//流程编码
	private String flowCode;
	//
	private String formId;
	//备注
	private String comment;
	//状态  0：禁用   1：正常
	private Integer status;
	//创建者ID
	private Long createUserId;
	//创建时间
	private Date createTime;

	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
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
	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}
	/**
	 * 获取：流程编码
	 */
	public String getFlowCode() {
		return flowCode;
	}
	/**
	 * 设置：
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}
	/**
	 * 获取：
	 */
	public String getFormId() {
		return formId;
	}
	/**
	 * 设置：备注
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * 获取：备注
	 */
	public String getComment() {
		return comment;
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
}
