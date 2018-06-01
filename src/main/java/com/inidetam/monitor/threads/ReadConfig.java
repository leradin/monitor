package com.inidetam.monitor.threads;

import java.util.concurrent.ExecutorService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import com.inidetam.monitor.jpa.OwnConfiguration;
import com.inidetam.monitor.pojo.Cabin;
import com.inidetam.monitor.utils.JsonRead;
import com.inidetam.monitor.utils.PropertiesRead;
import com.inidetam.monitor.utils.ScriptExecute;
import com.inidetam.monitor.utils.XmlRead;

public class ReadConfig implements Runnable {
	
	// Prepare our context and subscriber
	private Context context = ZMQ.context(1);
	
	// Socket susbcriber
	private Socket subscriber = context.socket(ZMQ.SUB);
	
	// Instance manager thread
	private ExecutorService executor;
		
	// Log
	private final static Logger logger = Logger.getLogger(ReadConfig.class.getName());
	
	// Constructor
	public ReadConfig(ExecutorService executor){
		this.executor = executor;
	}
	
	// run
	public void run() {
		logger.info("Listening...");
        subscriber.connect("tcp://"+PropertiesRead.getIpAddressServer()+":"+PropertiesRead.getPortServer());
        subscriber.subscribe(PropertiesRead.getTopicServer().getBytes());
        while (!Thread.currentThread ().isInterrupted ()) {
            // Read envelope with address
            String address = subscriber.recvStr ();
            // Read message contents
            String contents = subscriber.recvStr ();
            // Log Topic and content
            logger.info(address + " : " + contents);
            // managamente content
            inMessage(contents);
            
        }
        
		
	}
	
	private void inMessage(String contents){
		try {
			JSONObject object = (JSONObject)new JSONParser().parse(contents);
			JsonRead jsonRead = new JsonRead(object);
			Cabin cabin = jsonRead.searchDataCabin(PropertiesRead.getIdCabin());
			double [] positionNMEAInitial = DecimalToNMEAConverter(cabin.getUnit().getPositionInitial().get(0), cabin.getUnit().getPositionInitial().get(1));
			cabin.getUnit().setPositionNMEAInitial(positionNMEAInitial);
			update(cabin);
			new XmlRead(positionNMEAInitial[0]+","+positionNMEAInitial[1]);
			new ScriptExecute();
			logger.info(cabin.getId() +" : "+cabin.getUnit().getName());
		} catch (ParseException | NullPointerException e) {
			logger.error(e.getClass() +" : "+e.getMessage());
		}
		// Close socket sub
		subscriber.close ();
		// Close context zmq
        context.term ();
		// kill thread management
        executor.shutdownNow();
	}
	
	private void update(Cabin cabin){
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("monitor");
		EntityManager em = emf.createEntityManager();
		
		// Initialize transaction
		em.getTransaction().begin();
		
		// Prepare query for clear table 
		Query cleanTable =  em.createQuery("DELETE FROM OwnConfiguration");
		
		// execute query
		cleanTable.executeUpdate();

		// Create object for insert table
		OwnConfiguration ownConfiguration = new OwnConfiguration(); 
		ownConfiguration.setiLocalStationId((long) cabin.getUnit().getStation());
		ownConfiguration.setSzOwnShipName(cabin.getUnit().getName());
		ownConfiguration.setSzOwnShipNumeral(cabin.getUnit().getNumeral());
		ownConfiguration.setSzOwnShipCountryName("MEXICO");
		ownConfiguration.setSzOwnShipCountryCode("MX");
		ownConfiguration.setiLastAssignedSerialNumber((long) 88);
		
        em.persist(ownConfiguration);
        em.getTransaction().commit();
	    
	    em.close( );
	    emf.close( );
	}
	
	private double[] DecimalToNMEAConverter(double lat, double lng) {  
        String dcmLatString = null;  
        int latDegree = 0;  
        double latMinutes = 0, dcmLat = 0, absLat = 0;  
        
        String dcmLngString = null;  
        int lngDegree = 0;  
        double lngMinutes = 0, dcmLng = 0, absLng = 0;  
        
        logger.info("Decimal To NMEA: " + lat + " Longitude: "  + lng);  
        if (lat < 0) {  
             absLat = lat * -1;  
        } else {  
             absLat = lat;  
        }  
        
        latDegree = (int) absLat;  
        latMinutes = (absLat - latDegree) * 60;  
        dcmLatString = String.valueOf(latDegree)  
                  + String.valueOf(latMinutes);  
        dcmLat = Double.parseDouble(dcmLatString);  
        dcmLat = Math.round(dcmLat * 10000.0) / 10000.0;  
       
        if (lat < 0) {  
             dcmLat *= -1;  
        }  
        // ************************************************//  
        if (lng < 0) {  
             absLng = lng * -1;  
        } else {  
             absLng = lng;  
        }  
        
        lngDegree = (int) absLng;  
        lngMinutes = (absLng - lngDegree) * 60;  
        dcmLngString = String.valueOf(lngDegree)  
                  + String.valueOf(lngMinutes);  
        dcmLng = Double.parseDouble(dcmLngString);  
        dcmLng = Math.round(dcmLng * 10000.0) / 10000.0;  
       
        if (lng < 0) {  
             dcmLng *= -1;  
        }  
        logger.info("NMEA Lat: " + dcmLat + " Lng: " + dcmLng);  
        return (new double[] { dcmLat, dcmLng });  
   }  

}
