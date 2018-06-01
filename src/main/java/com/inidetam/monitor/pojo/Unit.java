package com.inidetam.monitor.pojo;

import java.util.ArrayList;
import java.util.List;

public class Unit {
	private int station;
	private String name;
	private String numeral;
	private List<Double> positionInitial = new ArrayList<Double>();
	private double [] positionNMEAInitial;
	
	
	public Unit() {
	}
	
	public Unit(int station, String name, String numeral, List<Double> positionInitial) {
		this.station = station;
		this.name = name;
		this.numeral = numeral;
		this.positionInitial = positionInitial;
	}

	public int getStation() {
		return station;
	}
	public void setStation(int station) {
		this.station = station;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumeral() {
		return numeral;
	}
	public void setNumeral(String numeral) {
		this.numeral = numeral;
	}

	public List<Double> getPositionInitial() {
		return positionInitial;
	}

	public void setPositionInitial(List<Double> positionInitial) {
		this.positionInitial = positionInitial;
	}

	public double[] getPositionNMEAInitial() {
		return positionNMEAInitial;
	}

	public void setPositionNMEAInitial(double[] positionNMEAInitial) {
		this.positionNMEAInitial = positionNMEAInitial;
	}
}
