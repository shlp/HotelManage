package com.ssm.hotel.entity;

import org.springframework.stereotype.Component;

/**
 * 房间实体类
 * @author Administrator
 *
 */
@Component
public class Room {
	private Long id;//房间id
	private String photo;//房间图片
	private String sn;//房间编号
	private Long roomTypeId;//房型id
	private Long floorId;//所属楼层id
	private int status;//房型状态，0：可入住,1:已入住,2:打扫中,3:检查无误，4;检查有误；5：维修中,6:加急打扫
	private String remark;//房型备注
	private String isWindow;//是否有窗
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Long getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(Long roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public Long getFloorId() {
		return floorId;
	}
	public void setFloorId(Long floorId) {
		this.floorId = floorId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getIsWindow() {
		return isWindow;
	}

	public void setIsWindow(String isWindow) {
		this.isWindow = isWindow;
	}
}
