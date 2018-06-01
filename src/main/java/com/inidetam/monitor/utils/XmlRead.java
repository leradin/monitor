package com.inidetam.monitor.utils;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


public class XmlRead {
	
	// Log
	private final static Logger logger = Logger.getLogger(XmlRead.class.getName());
		

	public XmlRead(String coordinatesNMEA){
		try {
			String filepath = PropertiesRead.getFileSedamSimulation();
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			// Get the staff element by tag name directly
			Node sensor = doc.getElementsByTagName("Sensor").item(0);
			NamedNodeMap attrSensor = sensor.getAttributes();
			Node simulateAttrSensor = attrSensor.getNamedItem("values");
			simulateAttrSensor.setTextContent(coordinatesFormatNMEA(coordinatesNMEA));
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			logger.error(pce.getClass() +" : "+pce.getMessage());
		} catch (IOException ioe) {
			logger.error(ioe.getClass() +" : "+ioe.getMessage());
		} catch (SAXException sae) {
			logger.error(sae.getClass() +" : "+sae.getMessage());
		} catch (TransformerConfigurationException e) {
			logger.error(e.getClass() +" : "+e.getMessage());
		} catch (TransformerException e) {
			logger.error(e.getClass() +" : "+e.getMessage());
		}
	}
	
	private String coordinatesFormatNMEA(String coordinates){
		
		String [] coordinatesFormatNMEA = coordinates.split(",");
		for(int i = 0; i < coordinatesFormatNMEA.length; i++){
			if(coordinatesFormatNMEA[i].contains("-")){
				coordinatesFormatNMEA[i] = coordinatesFormatNMEA[i].replace("-", "").concat((i==0)?",S":",W");
			}else{
				coordinatesFormatNMEA[i] = coordinatesFormatNMEA[i].replace("-", "").concat((i==0)?",N":",E");
			}
		}
		return coordinatesFormatNMEA[0]+","+coordinatesFormatNMEA[1];
		
	}
}
