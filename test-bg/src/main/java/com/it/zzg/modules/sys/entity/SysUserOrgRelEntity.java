package com.it.zzg.modules.sys.entity;


import java.io.Serializable;

/**
 * 用户与机构对应关系
 * 
 * @author admin
 * @email admin
 * @date 2016年9月18日 上午9:28:39
 */
public class SysUserOrgRelEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 角色ID
	 */
	private Long orgId;

	/**
	 * 设置：
	 * @param id 
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取：
	 * @return Long
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * 设置：用户ID
	 * @param userId 用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取：用户ID
	 * @return Long
	 */
	public Long getUserId() {
		return userId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
}
