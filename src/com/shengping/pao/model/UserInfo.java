package com.shengping.pao.model;

public class UserInfo {
	private String userName;
	private Coast coast;//���ȷ���ϸ
	private double money;//�û����
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Coast getCoast() {
		return coast;
	}

	public void setCoast(Coast coast) {
		this.coast = coast;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}
}
