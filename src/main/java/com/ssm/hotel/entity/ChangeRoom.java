package com.ssm.hotel.entity;

/*
换房实体类
 */
public class ChangeRoom {
    private Long id;//换房id
    private Long checkId;//入住信息Id
    private Long roomTypeId;//房型id
    private Long roomId;//房间id
    private float differPrice;//追加费用
    private String leaveDate;//离店日期
    private String changeRemark;//换房备注

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public float getDifferPrice() {
        return differPrice;
    }

    public void setDifferPrice(float differPrice) {
        this.differPrice = differPrice;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getChangeRemark() {
        return changeRemark;
    }

    public void setChangeRemark(String changeRemark) {
        this.changeRemark = changeRemark;
    }
}
