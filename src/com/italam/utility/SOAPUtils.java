package com.italam.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class SOAPUtils {
	
	private static final Logger log = LogManager.getLogger(SOAPUtils.class.getName());
	
		
	//This method will get an XML file and return it as String			
	public static String getSoapRequestFromFile(String xmlRequestFilePath) throws ParserConfigurationException, SAXException, IOException, TransformerException   {
		
		
		File fXmlFile = new File(xmlRequestFilePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);
	    
	    DOMSource domSource = new DOMSource(doc);
	    StringWriter writer = new StringWriter();
	    StreamResult result = new StreamResult(writer);
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.transform(domSource, result);
	    String xmlRequest=writer.toString();
	    return xmlRequest;
	    
	    //return createSoapWebService(soapEndpointUrl, soapAction,xmlRequest);
	    
	    
	}
	
	
	

	
	//This method will send a WS request and print to log its response.
		//The response will return as String
		 public static String sendSoapWebServiceWithAuthHeaders(String soapEndpointUrl, String soapAction, String xmlRequest, String soapAuthHeader) {
		        
			 	
			 	try {
		        		        	
		            // Create SOAP Connection
		            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		            		            
		            
		            // Send SOAP Message to SOAP Server		           
		            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction,xmlRequest,soapAuthHeader), soapEndpointUrl);
		            
		            // Print the SOAP Response
		            log.info("Response SOAP Message:");
		            ByteArrayOutputStream stream = new ByteArrayOutputStream();
		            soapResponse.writeTo(stream);
		            
		            //soapResponse.writeTo(System.out);
		            log.info(stream.toString());
		            soapConnection.close();
		            
		            return stream.toString();
		            
		        } catch (Exception e) {
		            log.error("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
		            e.printStackTrace();
		            return null;
		        }
		        
		        
		        
		    }
		 
		 
		 private static SOAPMessage createSOAPRequest(String soapAction,String xml,String base64DigestHeaderValue) throws Exception {
		        MessageFactory messageFactory = MessageFactory.newInstance();
		        
		        MimeHeaders headers = new MimeHeaders();
		        headers.addHeader("SOAPAction", soapAction);		        		        
		        headers.addHeader("Authorization", "Basic " + base64DigestHeaderValue);
		        
		        
		        
		        
		        SOAPMessage soapMessage = messageFactory.createMessage(headers, new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8"))));
		        
		    	
		        
		        
		        
		        //createSoapEnvelope(soapMessage);
		        
		        soapMessage.saveChanges();

		        /* Print the request message, just for debugging purposes */
		        log.info("Request SOAP Message:");
		        ByteArrayOutputStream stream = new ByteArrayOutputStream();            
		        soapMessage.writeTo(stream);
		        log.info("\n");
		        log.info(stream.toString());

		        return soapMessage;
		    }

	
	//This method will send a WS request and print to log its response.
	//The response will return as String
	 public static String sendSoapWebService(String soapEndpointUrl, String soapAction, String xmlRequest) {
	        
		 	
		 	try {
	        		        	
	            // Create SOAP Connection
	            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

	            // Send SOAP Message to SOAP Server
	            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction,xmlRequest), soapEndpointUrl);

	            // Print the SOAP Response
	            log.info("Response SOAP Message:");
	            ByteArrayOutputStream stream = new ByteArrayOutputStream();
	            soapResponse.writeTo(stream);
	            
	            //soapResponse.writeTo(System.out);
	            log.info(stream.toString());
	            soapConnection.close();
	            
	            return stream.toString();
	            
	        } catch (Exception e) {
	            log.error("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
	            e.printStackTrace();
	            return null;
	        }
	        
	        
	        
	    }
	 
	 private static SOAPMessage createSOAPRequest(String soapAction,String xml) throws Exception {
	        MessageFactory messageFactory = MessageFactory.newInstance();
	        
	        MimeHeaders headers = new MimeHeaders();
	        headers.addHeader("SOAPAction", soapAction);
	        
	        
	        SOAPMessage soapMessage = messageFactory.createMessage(headers, new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8"))));
	        
	    	
	        
	        
	        
	        //createSoapEnvelope(soapMessage);        
	        soapMessage.saveChanges();

	        /* Print the request message, just for debugging purposes */
	        log.info("Request SOAP Message:");
	        ByteArrayOutputStream stream = new ByteArrayOutputStream();            
	        soapMessage.writeTo(stream);
	        log.info("\n");
	        log.info(stream.toString());

	        return soapMessage;
	    }
	 
	 
	 	    

}
