package com.shengping.pao.model;

public class Coast {
	private int destence_min=3;//�𲽾���
	private int price_min=10;//�𲽷���
	private int step_price=2;//����ÿ������ս��
	private int ninght_price=5;//ҹ����ӷ���
	private int snow_price=5;//��ѩ����ӷ���
	public int getDestence_min() {
		return destence_min;
	}
	public void setDestence_min(int destence_min) {
		this.destence_min = destence_min;
	}
	public int getPrice_min() {
		return price_min;
	}
	public void setPrice_min(int price_min) {
		this.price_min = price_min;
	}
	public int getStep_price() {
		return step_price;
	}
	public void setStep_price(int step_price) {
		this.step_price = step_price;
	}
	public int getNinght_price() {
		return ninght_price;
	}
	public void setNinght_price(int ninght_price) {
		this.ninght_price = ninght_price;
	}
	public int getSnow_price() {
		return snow_price;
	}
	public void setSnow_price(int snow_price) {
		this.snow_price = snow_price;
	}
}
