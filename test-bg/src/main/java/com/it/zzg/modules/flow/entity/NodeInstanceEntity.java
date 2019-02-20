package com.it.zzg.modules.flow.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 流程节点实例表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:24
 */
public class NodeInstanceEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//业务ID
	private String id;
	private String formId;
	private String orgId;
	private String detailUrl;
	//节点编码
	private String nodeCode;
	//节点名称
	private String nodeName;
	private String userName;
	//流程编码
	private String flowCode;
	private String flowName;
	//节点x
	private String posX;
	//节点y
	private String posY;
	//节点z
	private String posZ;
	//节点I
	private String posI;
	//节点x
	private String posXName;
	//节点y
	private String posYName;
	//节点z
	private String posZName;
	//节点I
	private String posIName;
	private String xBeanMethod;
	private String yBeanMethod;
	private String zBeanMethod;
	private String iBeanMethod;
	// 1-开始 2-结束  3-普通  
	private Integer typ;
	//节点执行角色
	private String executeRole;
	private String executeRoleName;
	//节点状态  1-已经处理   2-未处理
	private Integer nodeStatus;
	//状态  
	private Integer status;
	//创建者ID
	private Long createUserId;
	//创建时间
	private Date createTime;
	private Integer rank;
	
	private String contCode;
	private String tenantName;
	
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getxBeanMethod() {
		return xBeanMethod;
	}
	public void setxBeanMethod(String xBeanMethod) {
		this.xBeanMethod = xBeanMethod;
	}
	public String getyBeanMethod() {
		return yBeanMethod;
	}
	public void setyBeanMethod(String yBeanMethod) {
		this.yBeanMethod = yBeanMethod;
	}
	public String getzBeanMethod() {
		return zBeanMethod;
	}
	public void setzBeanMethod(String zBeanMethod) {
		this.zBeanMethod = zBeanMethod;
	}
	public String getiBeanMethod() {
		return iBeanMethod;
	}
	public void setiBeanMethod(String iBeanMethod) {
		this.iBeanMethod = iBeanMethod;
	}
	public String getPosXName() {
		return posXName;
	}
	public void setPosXName(String posXName) {
		this.posXName = posXName;
	}
	public String getPosYName() {
		return posYName;
	}
	public void setPosYName(String posYName) {
		this.posYName = posYName;
	}
	public String getPosZName() {
		return posZName;
	}
	public void setPosZName(String posZName) {
		this.posZName = posZName;
	}
	public String getPosIName() {
		return posIName;
	}
	public void setPosIName(String posIName) {
		this.posIName = posIName;
	}
	public String getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public String getExecuteRoleName() {
		return executeRoleName;
	}
	public void setExecuteRoleName(String executeRoleName) {
		this.executeRoleName = executeRoleName;
	}
	/**
	 * 业务ID
	 */
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	/**
	 * 节点I
	 */
	public String getPosI() {
		return posI;
	}
	public void setPosI(String posI) {
		this.posI = posI;
	}
	
 
	public Integer getTyp() {
		return typ;
	}
	public void setTyp(Integer typ) {
		this.typ = typ;
	}
	/**
	 * 设置：节点编码
	 */
	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}
	/**
	 * 获取：节点编码
	 */
	public String getNodeCode() {
		return nodeCode;
	}
	/**
	 * 设置：节点名称
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	/**
	 * 获取：节点名称
	 */
	public String getNodeName() {
		return nodeName;
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
	 * 设置：节点x--下一节点
	 */
	public void setPosX(String posX) {
		this.posX = posX;
	}
	/**
	 * 获取：节点x
	 */
	public String getPosX() {
		return posX;
	}
	/**
	 * 设置：节点y
	 */
	public void setPosY(String posY) {
		this.posY = posY;
	}
	/**
	 * 获取：节点y
	 */
	public String getPosY() {
		return posY;
	}
	/**
	 * 设置：节点z
	 */
	public void setPosZ(String posZ) {
		this.posZ = posZ;
	}
	/**
	 * 获取：节点z
	 */
	public String getPosZ() {
		return posZ;
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
	 * 设置：节点状态  1-已经处理   2-未处理
	 */
	public void setNodeStatus(Integer nodeStatus) {
		this.nodeStatus = nodeStatus;
	}
	/**
	 * 获取：节点状态  1-已经处理   2-未处理
	 */
	public Integer getNodeStatus() {
		return nodeStatus;
	}
	/**
	 * 设置：状态  
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态  
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
	public String getContCode() {
		return contCode;
	}
	public void setContCode(String contCode) {
		this.contCode = contCode;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	
}
