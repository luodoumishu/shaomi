package com.xjj.ws.distribute;

import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.SOAPFaultException;
import javax.xml.namespace.QName;

import java.util.Iterator;
import java.util.Set;
import java.util.Collections;

@SuppressWarnings("rawtypes")
public class HandlerAuth implements SOAPHandler<SOAPMessageContext> {

    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

	public boolean handleMessage(SOAPMessageContext context) {
		
		Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (!outbound) {
			try {
				SOAPMessage soapMessage = context.getMessage();
				SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
				SOAPHeader soapHeader = soapEnvelope.getHeader();
				if (soapHeader == null) {
					soapHeader = soapEnvelope.addHeader();
					generateSOAPErrMessage(soapMessage, "No SOAP header.");
				}
				Iterator it = soapHeader.extractHeaderElements(SOAPConstants.URI_SOAP_ACTOR_NEXT);
				if (it == null || !it.hasNext()) {
					generateSOAPErrMessage(soapMessage,"No header block for next actor.");
				}

				String account = "xjjuser";
				String xjjuser =  "876543";
				while (it.hasNext()) {
					Node node = (Node) it.next();
					String nodeName = node.getNodeName();
					String value = node.getValue();
					if (account.equals(nodeName)) {
						if (xjjuser.equals(value)) {
							System.out.println("Auth Pass!");
							return true;
						} else {
							generateSOAPErrMessage(soapMessage, "Auth ERROR!");
							return false;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 

		}
		return false;
	}

    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    public void close(MessageContext context) {
    }
    
    private void generateSOAPErrMessage(SOAPMessage msg, String reason) {
		try {
			SOAPBody soapBody = msg.getSOAPPart().getEnvelope().getBody();
			SOAPFault soapFault = soapBody.addFault();
			soapFault.setFaultString(reason);
			throw new SOAPFaultException(soapFault);
		} catch (SOAPException e) {
			e.printStackTrace();
		}
	}
}
