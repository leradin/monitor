package com.inidetam.monitor.pojo;

public class Cabin {
	private int id;
	private String name;
	private Unit unit;
	
	
	public Cabin() {
	}

	public Cabin(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
}
