package com.it.zzg.modules.flow.entity;

import java.io.Serializable;
import java.util.List;

public class WorkFlowDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long userId;
	private String formId;
	private String orgId;
	private String nodeCode;
	private String flowCode;
	private String nodeSeq;//对应 Constants.NODE_SEQ
	private Integer status;
	private String comment;
	private String beanName;
	private String methonName;
	private String contId;
	private List<String> executeRoleList;

	public String getContId() {
		return contId;
	}

	public void setContId(String contId) {
		this.contId = contId;
	}

	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getMethonName() {
		return methonName;
	}
	public void setMethonName(String methonName) {
		this.methonName = methonName;
	}
	public List<String> getExecuteRoleList() {
		return executeRoleList;
	}
	public void setExecuteRoleList(List<String> executeRoleList) {
		this.executeRoleList = executeRoleList;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getNodeCode() {
		return nodeCode;
	}
	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}
	public String getFlowCode() {
		return flowCode;
	}
	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getNodeSeq() {
		return nodeSeq;
	}
	public void setNodeSeq(String nodeSeq) {
		this.nodeSeq = nodeSeq;
	}
	
}
