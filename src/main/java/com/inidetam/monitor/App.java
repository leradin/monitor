package com.inidetam.monitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.inidetam.monitor.threads.ReadConfig;

/**
 * Hello world!
 *
 */
public class App 
{
	private final static Logger logger = Logger.getLogger(App.class.getName());
	
    public static void main( String[] args )
    {
    	BasicConfigurator.configure();
    	logger.info("Init App");
    	ExecutorService executor = Executors.newFixedThreadPool(2);
    	executor.execute(new ReadConfig(executor));
    }
}
