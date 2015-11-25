package com.shengping.pao.model;

import java.io.Serializable;


public class Address implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -3786555507999247605L;

	//�û��ջ���ַ��
	private int Id;

	private int UserId;//�û����

	private String RealName;//����
	private int ProvinceId;//
	private int CityId;
	private int DistrictId;

	private String Address;//�ջ���ַ
	private String Zip;

	private String Mobile;//�ֻ�
	private String QQ;
	private int OrderIndex;//����

	private String sGPRS;//�ջ�����

	private String sex;//������Ůʿ
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public String getRealName() {
		return RealName;
	}
	public void setRealName(String realName) {
		RealName = realName;
	}
	public int getProvinceId() {
		return ProvinceId;
	}
	public void setProvinceId(int provinceId) {
		ProvinceId = provinceId;
	}
	public int getCityId() {
		return CityId;
	}
	public void setCityId(int cityId) {
		CityId = cityId;
	}
	public int getDistrictId() {
		return DistrictId;
	}
	public void setDistrictId(int districtId) {
		DistrictId = districtId;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getZip() {
		return Zip;
	}
	public void setZip(String zip) {
		Zip = zip;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	public String getQQ() {
		return QQ;
	}
	public void setQQ(String qQ) {
		QQ = qQ;
	}
	public int getOrderIndex() {
		return OrderIndex;
	}
	public void setOrderIndex(int orderIndex) {
		OrderIndex = orderIndex;
	}
	public String getsGPRS() {
		return sGPRS;
	}
	public void setsGPRS(String sGPRS) {
		this.sGPRS = sGPRS;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
}
