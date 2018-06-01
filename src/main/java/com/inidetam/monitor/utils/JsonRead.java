package com.inidetam.monitor.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import com.inidetam.monitor.pojo.Cabin;
import com.inidetam.monitor.pojo.Unit;

public class JsonRead {
	JSONObject root;
	
	public JsonRead(JSONObject root){
		this.root = root;
	}
	
	private JSONObject getNodesCabins(){
		JSONObject stage = (JSONObject) root.get("stage");
		JSONObject cabins = (JSONObject) stage.get("cabins");
		return cabins; 
	}
	
	public Cabin searchDataCabin(int idCabin){
		JSONObject cabins = getNodesCabins();
		Cabin cabinObject = null;
		
		for (Object cabin : cabins.keySet()) {
	        //based on you key types
	        String keyCabin = (String)cabin;
	        //Object valueCabin = cabins.get(keyCabin);
	        
	        JSONObject propertiesCabin = (JSONObject) cabins.get(keyCabin);
	        JSONObject positionInitialCabin = (JSONObject) propertiesCabin.get("init_position");
	        JSONObject unitCabin = (JSONObject) propertiesCabin.get("unit");
	        JSONObject dataCabin = (JSONObject) propertiesCabin.get("cabin");
	        
	        if(Integer.parseInt(dataCabin.get("id").toString()) == idCabin){
	        	String name = (String) unitCabin.get("name");
	 	        int station = Integer.parseInt(unitCabin.get("station").toString());
	 	        String numeral = (String) unitCabin.get("numeral");
	 	        List<Double> positionInitial = new ArrayList<Double>();
	 	        positionInitial.add(Double.parseDouble(positionInitialCabin.get("lat").toString()));
	 	        positionInitial.add(Double.parseDouble(positionInitialCabin.get("lon").toString()));
	 	        
	 	        cabinObject = new Cabin(Integer.parseInt(dataCabin.get("id").toString()),dataCabin.get("name").toString());
	 	        Unit unitObject = new Unit(station, name, numeral, positionInitial);
	 	        cabinObject.setUnit(unitObject);
	        }
	       
		}
		
		return cabinObject;
	}			
}
