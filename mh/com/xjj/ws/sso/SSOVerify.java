
package com.xjj.ws.sso;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "SSOVerify", targetNamespace = "http://sso.ws.xjj.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SSOVerify {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getUserInfo", targetNamespace = "http://sso.ws.xjj.com/", className = "com.xjj.ws.sso.GetUserInfo")
    @ResponseWrapper(localName = "getUserInfoResponse", targetNamespace = "http://sso.ws.xjj.com/", className = "com.xjj.ws.sso.GetUserInfoResponse")
    public String getUserInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "userLogin", targetNamespace = "http://sso.ws.xjj.com/", className = "com.xjj.ws.sso.UserLogin")
    @ResponseWrapper(localName = "userLoginResponse", targetNamespace = "http://sso.ws.xjj.com/", className = "com.xjj.ws.sso.UserLoginResponse")
    public String userLogin(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "checkLoginByToken", targetNamespace = "http://sso.ws.xjj.com/", className = "com.xjj.ws.sso.CheckLoginByToken")
    @ResponseWrapper(localName = "checkLoginByTokenResponse", targetNamespace = "http://sso.ws.xjj.com/", className = "com.xjj.ws.sso.CheckLoginByTokenResponse")
    public String checkLoginByToken(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "checkLoginByCookie", targetNamespace = "http://sso.ws.xjj.com/", className = "com.xjj.ws.sso.CheckLoginByCookie")
    @ResponseWrapper(localName = "checkLoginByCookieResponse", targetNamespace = "http://sso.ws.xjj.com/", className = "com.xjj.ws.sso.CheckLoginByCookieResponse")
    public String checkLoginByCookie(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "userLogout", targetNamespace = "http://sso.ws.xjj.com/", className = "com.xjj.ws.sso.UserLogout")
    @ResponseWrapper(localName = "userLogoutResponse", targetNamespace = "http://sso.ws.xjj.com/", className = "com.xjj.ws.sso.UserLogoutResponse")
    public Boolean userLogout(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getUserInfoByAccount", targetNamespace = "http://sso.ws.xjj.com/", className = "com.xjj.ws.sso.GetUserInfoByAccount")
    @ResponseWrapper(localName = "getUserInfoByAccountResponse", targetNamespace = "http://sso.ws.xjj.com/", className = "com.xjj.ws.sso.GetUserInfoByAccountResponse")
    public String getUserInfoByAccount(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getUserOrg", targetNamespace = "http://sso.ws.xjj.com/", className = "com.xjj.ws.sso.GetUserOrg")
    @ResponseWrapper(localName = "getUserOrgResponse", targetNamespace = "http://sso.ws.xjj.com/", className = "com.xjj.ws.sso.GetUserOrgResponse")
    public String getUserOrg(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getUserOrgByAccount", targetNamespace = "http://sso.ws.xjj.com/", className = "com.xjj.ws.sso.GetUserOrgByAccount")
    @ResponseWrapper(localName = "getUserOrgByAccountResponse", targetNamespace = "http://sso.ws.xjj.com/", className = "com.xjj.ws.sso.GetUserOrgByAccountResponse")
    public String getUserOrgByAccount(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getOrgTreeByOrgId", targetNamespace = "http://sso.ws.xjj.com/", className = "com.xjj.ws.sso.GetOrgTreeByOrgId")
    @ResponseWrapper(localName = "getOrgTreeByOrgIdResponse", targetNamespace = "http://sso.ws.xjj.com/", className = "com.xjj.ws.sso.GetOrgTreeByOrgIdResponse")
    public String getOrgTreeByOrgId(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

}
