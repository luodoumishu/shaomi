package com.xjj.ws.sso;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class AuthHandlerClient implements SOAPHandler<SOAPMessageContext> {
	
	private final static String PROP_NAME = AuthHandlerClient.class.getPackage().getName().replace(".", "/")+
								"/wsconfig.properties";
	public Set<QName> getHeaders() {
		return null;
	}
	public void close(MessageContext context) {
	}
	public boolean handleFault(SOAPMessageContext context) {
		return false;
	}

	public boolean handleMessage(SOAPMessageContext context) {
		Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (outbound.booleanValue()) {
			try {
				SOAPMessage soapMessage = context.getMessage();
				SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
				SOAPHeader soapHeader = soapEnvelope.getHeader();
				if (soapHeader == null) {
					soapHeader = soapEnvelope.addHeader();
				}
				QName authName = new QName(PropertiesUtil.getProperty(PROP_NAME, "NAMESPACE"),
											PropertiesUtil.getProperty(PROP_NAME, "WS_USERNAME")
											);
				SOAPHeaderElement authEle = soapHeader.addHeaderElement(authName);
				//authEle.setActor(SOAPConstants.URI_SOAP_1_2_ROLE_NEXT);
				authEle.setActor(SOAPConstants.URI_SOAP_ACTOR_NEXT);
				authEle.addTextNode(PropertiesUtil.getProperty(PROP_NAME, "WS_PASSWORD"));
				soapMessage.saveChanges();
				//soapMessage.writeTo(System.out);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
