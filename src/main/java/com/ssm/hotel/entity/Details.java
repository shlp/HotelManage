package com.ssm.hotel.entity;

/*
换房实体类
 */
public class Details {
    private Long id;//换房id
    private Long checkId;//入住信息Id
    private Long roomTypeId;//房型id
    private Long roomTypeIdByChange;//换房类型id
    private float differPrice;//追加费用
    private float lossPrice;//赔损费用
    private float checkinPrice;//入住价格
    private float paid;//总收益
    private String leaveDate;//离店日期
    private String arriveDate;//到店日期

    public Long getRoomTypeIdByChange() {
        return roomTypeIdByChange;
    }

    public void setRoomTypeIdByChange(Long roomTypeIdByChange) {
        this.roomTypeIdByChange = roomTypeIdByChange;
    }

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

    public float getDifferPrice() {
        return differPrice;
    }

    public void setDifferPrice(float differPrice) {
        this.differPrice = differPrice;
    }

    public float getLossPrice() {
        return lossPrice;
    }

    public void setLossPrice(float lossPrice) {
        this.lossPrice = lossPrice;
    }

    public float getCheckinPrice() {
        return checkinPrice;
    }

    public void setCheckinPrice(float checkinPrice) {
        this.checkinPrice = checkinPrice;
    }

    public float getPaid() {
        return paid;
    }

    public void setPaid(float paid) {
        this.paid = paid;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(String arriveDate) {
        this.arriveDate = arriveDate;
    }
}
