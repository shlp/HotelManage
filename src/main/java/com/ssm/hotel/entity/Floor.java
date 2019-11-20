package com.ssm.hotel.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 楼层实体类
 * @author Administrator
 *
 */
@Component
public class Floor {
	private Long id;//楼层id
	private String name;//楼层名称
	private String remark;//楼层备注
	private Integer status;//楼层状态 0:正常 -1：维修
	private String startTime;//楼层维修开始时间
	private String stopTime;//楼层维修结束时间

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
}

