package com.shengping.pao.model;

import org.json.JSONObject;

public class PaotuiInfo {
	private String Name;//���ȹ�˾����
	private int Id;//���ȹ�˾id
	private String Kefu;//���ȹ�˾�ͷ��绰
	private String Time;//���ȹ�˾Ӫҵʱ��
	private int MRGLS;//Ĭ�Ϲ�����
	private double LJPTF;//����ÿ�������
	private double qibujia;//�𲽼�
	private String tianqi;//�������
	private double tianqifei;//��������
	private JSONObject midnight_time;//��ҹ����ʱ��{"start":"23:00","end":"4:00"}
	private double midnight_cost;//��ҹ�����
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getKefu() {
		return Kefu;
	}
	public void setKefu(String kefu) {
		Kefu = kefu;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public int getMRGLS() {
		return MRGLS;
	}
	public void setMRGLS(int mRGLS) {
		MRGLS = mRGLS;
	}
	public double getLJPTF() {
		return LJPTF;
	}
	public void setLJPTF(double lJPTF) {
		LJPTF = lJPTF;
	}
	public double getQibujia() {
		return qibujia;
	}
	public void setQibujia(double qibujia) {
		this.qibujia = qibujia;
	}
	public String getTianqi() {
		return tianqi;
	}
	public void setTianqi(String tianqi) {
		this.tianqi = tianqi;
	}
	public double getTianqifei() {
		return tianqifei;
	}
	public void setTianqifei(double tianqifei) {
		this.tianqifei = tianqifei;
	}
	public JSONObject getMidnight_time() {
		return midnight_time;
	}
	public void setMidnight_time(JSONObject midnight_time) {
		this.midnight_time = midnight_time;
	}
	public double getMidnight_cost() {
		return midnight_cost;
	}
	public void setMidnight_cost(double midnight_cost) {
		this.midnight_cost = midnight_cost;
	}
}
