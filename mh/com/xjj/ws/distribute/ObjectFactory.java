
package com.xjj.ws.distribute;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xjj.ws.distribute package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _OrgDistributeResponse_QNAME = new QName("http://distribute.ws.xjj.com/", "orgDistributeResponse");
    private final static QName _OrgDistribute_QNAME = new QName("http://distribute.ws.xjj.com/", "orgDistribute");
    private final static QName _UserDistributeResponse_QNAME = new QName("http://distribute.ws.xjj.com/", "userDistributeResponse");
    private final static QName _UserDistribute_QNAME = new QName("http://distribute.ws.xjj.com/", "userDistribute");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xjj.ws.distribute
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OrgDistributeResponse }
     * 
     */
    public OrgDistributeResponse createOrgDistributeResponse() {
        return new OrgDistributeResponse();
    }

    /**
     * Create an instance of {@link UserDistribute }
     * 
     */
    public UserDistribute createUserDistribute() {
        return new UserDistribute();
    }

    /**
     * Create an instance of {@link OrgDistribute }
     * 
     */
    public OrgDistribute createOrgDistribute() {
        return new OrgDistribute();
    }

    /**
     * Create an instance of {@link UserDistributeResponse }
     * 
     */
    public UserDistributeResponse createUserDistributeResponse() {
        return new UserDistributeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrgDistributeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://distribute.ws.xjj.com/", name = "orgDistributeResponse")
    public JAXBElement<OrgDistributeResponse> createOrgDistributeResponse(OrgDistributeResponse value) {
        return new JAXBElement<OrgDistributeResponse>(_OrgDistributeResponse_QNAME, OrgDistributeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrgDistribute }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://distribute.ws.xjj.com/", name = "orgDistribute")
    public JAXBElement<OrgDistribute> createOrgDistribute(OrgDistribute value) {
        return new JAXBElement<OrgDistribute>(_OrgDistribute_QNAME, OrgDistribute.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserDistributeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://distribute.ws.xjj.com/", name = "userDistributeResponse")
    public JAXBElement<UserDistributeResponse> createUserDistributeResponse(UserDistributeResponse value) {
        return new JAXBElement<UserDistributeResponse>(_UserDistributeResponse_QNAME, UserDistributeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserDistribute }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://distribute.ws.xjj.com/", name = "userDistribute")
    public JAXBElement<UserDistribute> createUserDistribute(UserDistribute value) {
        return new JAXBElement<UserDistribute>(_UserDistribute_QNAME, UserDistribute.class, null, value);
    }

}
