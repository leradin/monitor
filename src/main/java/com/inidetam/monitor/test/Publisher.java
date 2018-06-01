package com.inidetam.monitor.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import com.inidetam.monitor.pojo.Cabin;
import com.inidetam.monitor.pojo.Unit;
import com.inidetam.monitor.utils.PropertiesRead;

public class Publisher {
	public static void main(String [] args) throws InterruptedException{
		// Prepare our context and publisher
        Context context = ZMQ.context(1);
        Socket publisher = context.socket(ZMQ.PUB);
        JSONObject root = null;
                
        // read file json
        String path = System.getProperty("user.dir")+"/test.json";
        BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			root = (JSONObject)new JSONParser().parse(bufferedReader);
			
			JSONObject stage = (JSONObject) root.get("stage");
			JSONObject cabins = (JSONObject) stage.get("cabins");
						
			for (Object cabin : cabins.keySet()) {
		        //based on you key types
		        String keyCabin = (String)cabin;
		        //Object valueCabin = cabins.get(keyCabin);
		        
		        JSONObject propertiesCabin = (JSONObject) cabins.get(keyCabin);
		        JSONObject positionInitialCabin = (JSONObject) propertiesCabin.get("init_position");
		        JSONObject unitCabin = (JSONObject) propertiesCabin.get("unit");
		        JSONObject dataCabin = (JSONObject) propertiesCabin.get("cabin");
		        
		        String name = (String) unitCabin.get("name");
		        int station = Integer.parseInt(unitCabin.get("station").toString());
		        String numeral = (String) unitCabin.get("numeral");
		        List<Double> positionInitial = new ArrayList<Double>();
		        positionInitial.add(Double.parseDouble(positionInitialCabin.get("lat").toString()));
		        positionInitial.add(Double.parseDouble(positionInitialCabin.get("lon").toString()));
		        
		        Cabin cabinObject = new Cabin(Integer.parseInt(dataCabin.get("id").toString()),dataCabin.get("name").toString());
		        Unit unitObject = new Unit(station, name, numeral, positionInitial);
		        cabinObject.setUnit(unitObject);
			}
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        publisher.bind("tcp://*:"+PropertiesRead.getPortServer());
        while (!Thread.currentThread ().isInterrupted ()) {
        	Thread.sleep(2000);
            // Write two messages, each with an envelope and content
            publisher.sendMore (PropertiesRead.getTopicServer());
            publisher.send(root.toJSONString().getBytes());
            System.out.println("...");
        }
        publisher.close ();
        context.term ();
	}
}
