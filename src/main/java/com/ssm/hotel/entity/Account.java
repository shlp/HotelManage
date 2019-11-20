package com.ssm.hotel.entity;

import org.springframework.stereotype.Component;

/**
 * 用户实体类
 * @author Administrator
 *
 */
@Component
public class Account {
	private Long id;//客户id
	private String name;//客户登录名
	private String password;//客户登录密码
	private String realName;//真实姓名
	private String idCard;//身份证号码
	private String mobile;//手机号
	private String address;//联系地址
	private int status;//状态：0：可用，-1：冻结
	private Long roleId;//所属角色id
	private String photo;//头像照片地址
	private int sex;//性别0,代表未知，1代表男，2代表女

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getRoleId() { return roleId; }
	public void setRoleId(Long roleId) { this.roleId = roleId; }
	public String getPhoto() { return photo; }
	public void setPhoto(String photo) { this.photo = photo; }
	public int getSex() { return sex; }
	public void setSex(int sex) { this.sex = sex; }
}
