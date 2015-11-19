package com.shengping.pao.adapter;

import java.util.List;

public class adapterData {
	private List<Integer> icons;
	private List<String> names;
	public int getCount(){
		return icons.size();
	}
	public List<Integer> getIcons() {
		return icons;
	}
	public void setIcons(List<Integer> icons) {
		this.icons = icons;
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
}
