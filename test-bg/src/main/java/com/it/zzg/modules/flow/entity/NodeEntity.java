package com.it.zzg.modules.flow.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 流程节点表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:24
 */
public class NodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//节点编码
	private String nodeCode;
	//节点名称
	private String nodeName;
	//流程编码
	private String flowCode;
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
	//是否是根节点 1-是 2-否
	private Integer typ;
	//节点执行角色
	private String executeRole;
	private String executeRoleName;
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
	private Integer rank;
	
	
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
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
	public String getExecuteRoleName() {
		return executeRoleName;
	}
	public void setExecuteRoleName(String executeRoleName) {
		this.executeRoleName = executeRoleName;
	}
	/**
	 * 设置：节点编码
	 */
	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}
	/**
	 * 节点I
	 */
	public String getPosI() {
		return posI;
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
	
	
	public Integer getTyp() {
		return typ;
	}
	public void setTyp(Integer typ) {
		this.typ = typ;
	}
	public void setPosI(String posI) {
		this.posI = posI;
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
	 * 设置：节点x
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
