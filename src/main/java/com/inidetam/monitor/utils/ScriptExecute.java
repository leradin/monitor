package com.inidetam.monitor.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;

public class ScriptExecute {
	
	// Log
	private final static Logger logger = Logger.getLogger(ScriptExecute.class.getName());
	
	public ScriptExecute(){
		Process process;
		try {
			process = Runtime.getRuntime().exec(PropertiesRead.getFileRebootPc());
			process.waitFor();
			
			BufferedReader reader = 
			         new BufferedReader(new InputStreamReader(process.getInputStream()));

			    String line = "";			
			    while ((line = reader.readLine())!= null) {
			    	logger.info(line + "\n");
			    }
		} catch (IOException e) {
			logger.error(e.getClass() +" : "+e.getMessage());
		} catch (InterruptedException e) {
			logger.error(e.getClass() +" : "+e.getMessage());
		}
	}
}
