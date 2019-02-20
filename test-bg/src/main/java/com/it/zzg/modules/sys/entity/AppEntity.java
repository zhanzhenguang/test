package com.it.zzg.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 系统日志
 * 
 * @author admin
 * @email admin
 * @date 2018-07-06 09:41:23
 */
public class AppEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//app名称
	private String appName;
	//app密码
	private String password;
	//app版本
	private String appV;
	//小程序签名
	private String signature;
	//小程序appId
	private String appid;
	//小程序秘钥
	private String secret;
	//微信商户支付账户
	private String payAcount;
	//微信支付的商户密钥
	private String paySecret;
	//微信支付的商户id
	private String paySign;
	//
	private String payPwd;
	//支付回调URl
	private String notifyUrl;
	//
	private String rank;
	//
	private Date createTime;
	//
	private Date updateTime;
	//
	private String createBy;
	//
	private Date updateBy;
	//
	private String status;

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
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 设置：app名称
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}
	/**
	 * 获取：app名称
	 */
	public String getAppName() {
		return appName;
	}
	/**
	 * 设置：app版本
	 */
	public void setAppV(String appV) {
		this.appV = appV;
	}
	/**
	 * 获取：app版本
	 */
	public String getAppV() {
		return appV;
	}
	/**
	 * 设置：小程序签名
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}
	/**
	 * 获取：小程序签名
	 */
	public String getSignature() {
		return signature;
	}
	/**
	 * 设置：小程序appId
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}
	/**
	 * 获取：小程序appId
	 */
	public String getAppid() {
		return appid;
	}
	/**
	 * 设置：小程序秘钥
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}
	/**
	 * 获取：小程序秘钥
	 */
	public String getSecret() {
		return secret;
	}
	/**
	 * 设置：微信商户支付账户
	 */
	public void setPayAcount(String payAcount) {
		this.payAcount = payAcount;
	}
	/**
	 * 获取：微信商户支付账户
	 */
	public String getPayAcount() {
		return payAcount;
	}
	/**
	 * 设置：微信支付的商户密钥
	 */
	public void setPaySecret(String paySecret) {
		this.paySecret = paySecret;
	}
	/**
	 * 获取：微信支付的商户密钥
	 */
	public String getPaySecret() {
		return paySecret;
	}
	/**
	 * 设置：微信支付的商户id
	 */
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
	/**
	 * 获取：微信支付的商户id
	 */
	public String getPaySign() {
		return paySign;
	}
	/**
	 * 设置：
	 */
	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}
	/**
	 * 获取：
	 */
	public String getPayPwd() {
		return payPwd;
	}
	/**
	 * 设置：支付回调URl
	 */
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	/**
	 * 获取：支付回调URl
	 */
	public String getNotifyUrl() {
		return notifyUrl;
	}
	/**
	 * 设置：
	 */
	public void setRank(String rank) {
		this.rank = rank;
	}
	/**
	 * 获取：
	 */
	public String getRank() {
		return rank;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：
	 */
	public void setUpdateBy(Date updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateBy() {
		return updateBy;
	}
	/**
	 * 设置：
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：
	 */
	public String getStatus() {
		return status;
	}
}
